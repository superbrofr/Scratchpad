# Create your views here.
from django.shortcuts import render, get_object_or_404
from django.http import HttpResponse, Http404, HttpResponseRedirect
from django.core.urlresolvers import reverse

from polls.models import Poll, Choice

# A simple view - needs to be mapped to a URL in URLconf.
# A URLconf is pure python code mapping between URL patterns (regex) to python callback functions (views).
def index(request):
	latest_poll_list = Poll.objects.order_by('-pub_date')[:5]
	context = {'latest_poll_list' : latest_poll_list}
	# render(request_object, template_name, optional_dictionary) - returns a corresponding HttpRequest object
	return render(request, 'polls/index.html', context)

def detail(request, poll_id):
	# alternatively, get_list_or_404()
	poll = get_object_or_404(Poll, pk=poll_id) # takes a django model, then an arbitrary no. of arguments which are passed to get(). Http404 is raised if the object doesn't exist
	return render(request, 'polls/details.html', {'poll' : poll})

def results(request, poll_id):
	poll = get_object_or_404(Poll, pk=poll_id)
	return render(request, 'polls/results.html', {'poll' : poll})

def vote(request, poll_id):
	p = get_object_or_404(Poll, pk=poll_id)
	try:
		# request.POST is like a dictionary; lets you access submitted data by key name
		# there is also a request.GET
		# if the key given to request.POST/GET isn't known, KeyError is raised
		selected_choice = p.choice_set.get(pk=request.POST['choice'])
	except (KeyError, Choice.DoesNotExist):
		# Re-display poll voting form
		return render(request, 'polls/details.html', {
			'poll' : p,
			'error_message' : "You didn't select a choice.",
		})
	else:
		selected_choice.votes += 1
		selected_choice.save()
		# Always return a HttpResponseRedirect after successfully dealing with POST data.
		# This prevents data from being posted twice if the user clicks 'Back'.
		# Takes one argument - the URL to which the user will be redirected
		# reverse() takes the name of the view you want to pass control to, and then any variable portions of that URL
		return HttpResponseRedirect(reverse('polls:results', args=(p.id,))) # args need to have the comma at the end to avoid a TypeError being thrown
