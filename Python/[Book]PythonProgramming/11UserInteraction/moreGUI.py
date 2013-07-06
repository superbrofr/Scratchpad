import Tkinter

class textbox(Tkinter.Frame):
	def __init__(self, parent, msg):
		Tkinter.Frame.__init__(self, parent)
		# create label on LHS
		self.g_label = Tkinter.Label(self, text = msg)
		self.g_label.pack(side = Tkinter.LEFT, expand = False)
		# create entry on RHS
		self.g_entry = Tkinter.Entry(self)
		self.g_entry.pack(side = Tkinter.LEFT, fill = Tkinter.X, expand = True)
		# pack the frame
		self.pack(fill = Tkinter.X, anchor = Tkinter.NW, expand = True)
		
	def text(self):
		return self.gui["entry"].get()
		
win = Tkinter.Tk()
name = textbox(win, "Name: ")

win.mainloop()