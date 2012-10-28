# This global urls file maps different applications in the Django project to file patterns.

from django.conf.urls import patterns, include, url

# Uncomment the next two lines to enable the admin:
from django.contrib import admin
admin.autodiscover()

# When a page is requested, Django starts at the first regex and makes its way down the list until it finds a match
# The regexes are compiled the first time URLconf module is loaded - they should be fast as long as they aren't too complex.
# When django finds a regex that matches, it calls the specified view function with a HttpRequest object and any captured values.

urlpatterns = patterns('',
    # Examples:
    # url(r'^$', 'mysite.views.home', name='home'),
    # url(r'^mysite/', include('mysite.foo.urls')),

    # Uncomment the admin/doc line below to enable admin documentation:
    # url(r'^admin/doc/', include('django.contrib.admindocs.urls')),

	# url(regex, view, name, kwargs)
	url(r'^polls/', include('polls.urls', namespace='polls')), # wires the site/polls page to the polls.urls definitions
    url(r'^admin/', include(admin.site.urls)),
)
