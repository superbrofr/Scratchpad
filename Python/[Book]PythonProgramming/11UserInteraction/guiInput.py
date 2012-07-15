import Tkinter

def retrieve_text():
	print app_entry.get()
	
if __name__ == "__main__":
	app_win = Tkinter.Tk() # create window
	# create label
	app_label = Tkinter.Label(app_win, text = "Hello World")
	app_label.pack()
	# create user input
	app_entry = Tkinter.Entry(app_win)
	app_entry.pack()
	# create button
	app_button = Tkinter.Button(app_win, text = "Print Value", command = retrieve_text)
	app_button.pack()
	
	# start graphical loop
	app_win.mainloop()