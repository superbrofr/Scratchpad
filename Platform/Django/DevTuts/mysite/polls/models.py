import datetime
from django.utils import timezone
from django.db import models

# Each class is a table in the database.
# Fields relate to different columns; each field is represented by an instance of `Field` classes.
# model.Model is the superclass of each table definition.

class Poll(models.Model):
	def __unicode__(self):
		# Essentially Java's toString()
		# Used instead of __str__ because Django models deal with Unicode by default.
		# Django models have a default __str__ method that calls __unicode__. unicode(p) will return a unicode string, and str(p) will return a UTF-8 string.
		return self.question

	def was_published_recently(self):
		return self.pub_date >= timezone.now() - datetime.timedelta(days=1)
	was_published_recently.admin_order_field = 'pub_date'
	was_published_recently.boolean = True
	was_published_recently.short_description = 'Published recently?'

	question = models.CharField(max_length=200) # max_length serves as both schema and validation specifications
	pub_date = models.DateTimeField('date published') # the parameter explicitly defines the column name - without this the variable name is used

class Choice(models.Model):
	def __unicode__(self):
		return self.choice_text

	poll = models.ForeignKey(Poll) # each choice is related to a single `Poll` object
	choice_text = models.CharField(max_length=200)
	votes = models.IntegerField()
