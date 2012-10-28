# Create your views here.
from django.shortcuts import render, get_object_or_404
from django.http import HttpResponse, Http404, HttpResponseRedirect
from django.core.urlresolvers import reverse

from polls.models import Poll, Choice

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
