class Book
{
    // The fields.
    private String author;
    private String title;
    private int pages;
    private String refnumber;
    private int borrowed;

    /**
     * Set the author and title fields when this object
     * is constructed.
     */
    public Book(String bookAuthor, String bookTitle, int noPages)
    {
        author = bookAuthor;
        title = bookTitle;
        pages = noPages;
        refnumber = "";
    }
    
    public void setRefNumber(String ref)
    {
        if(ref.length() < 3){
            System.out.println("Invalid reference number.");
        }
        else{
            refnumber = ref;
        }
    }
    
    public void borrow()
    {
        borrowed = borrowed + 1;
    }
    
    public String getRefNumber()
    {
        return refnumber;
    }
    
    public String getAuthor()
    {
        return author;
    }
    
    public int getPages()
    {
        return pages;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public int getBorrowed()
    {
        return borrowed;
    }
    
    public void printAuthor()
    {
        System.out.println("Author: " + author);
    }
    
    public void printTitle()
    {
        System.out.println("Title: " + title);
    }
    
    public void printDetails()
    {
        System.out.println("Author: " + author);
        System.out.println("Title: " + title);
        System.out.println("Pages: " + pages);
        if(refnumber.length() > 0){
            System.out.println("Reference: " + refnumber);
        }
        else{
            System.out.println("Reference: ZZZ");
        }
        System.out.println("No. Times Borrowed: " + borrowed);
    }
}
