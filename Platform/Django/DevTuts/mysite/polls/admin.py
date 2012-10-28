# This file allows the `Polls` app to be seen and managed from the website admin page.

from django.contrib import admin
from polls.models import Poll, Choice

# This creates an inline object, so Choice objects don't have to have a dedicated page.
# Alternative: admin.StackedInline
class ChoiceInline(admin.TabularInline):
	model = Choice
	extra = 3 # required number of choices

# Whenever you want to change admin options for an object, create a `ModelAdmin` version of it, then pass it in as the second parameter to `admin.site.register`.
class PollAdmin(admin.ModelAdmin):
# 	fields = ['pub_date', 'question'] # specifies the order in which fields appear on the admin page.
	# The first element is the title of the fieldset.
	# Then you define the fields that are part of that fieldset.
	# You can assign HTML classes to each fieldset. 'collapse' is provided by Django.
	fieldsets = [
		(None, {'fields': ['question']}),
		('Date information', {'fields': ['pub_date'], 'classes': ['collapse']})
	]
	inlines = [ChoiceInline] # adds the 3 choices to the Poll admin page
	list_display = ('question', 'pub_date', 'was_published_recently') # the order of display on the admin `edit poll` page
	list_filter = ['pub_date'] # allows admins to filter the change list by the 'pub_date' field
	search_fields = ['question'] # adds a search field to the top of the admin page
	date_hierarchy = 'pub_date' # adds hierarchical navigation by date

# Register objects with the admin page.
admin.site.register(Poll, PollAdmin)
