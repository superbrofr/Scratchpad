

/**
 * The test class mailtesting.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class mailtesting extends junit.framework.TestCase
{
	private Internet internet1;
	private MailServer mailServ1;
	private MailServer mailServ2;
	private MailClient bob;
	private MailClient fred;

    /**
     * Default constructor for test class mailtesting
     */
    public mailtesting()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    protected void setUp()
    {
		internet1 = new Internet();
		mailServ1 = new MailServer("mailServ1", internet1);
		mailServ2 = new MailServer("mailServ2", internet1);
		bob = new MailClient(mailServ1, "bob", "mailServ1");
		fred = new MailClient(mailServ2, "fred", "mailServ2");
	}

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    protected void tearDown()
    {
    }

	public void testAddMailServertoInternet()
	{
		assertEquals(true, internet1.addServer(mailServ1));
	}

	public void testCreateMailItem()
	{
		MailClient bob = new MailClient(mailServ1, "bob", "mailServ1");
		bob.sendMailItem("bob@mailServ1", "hey me");
		assertEquals(0, mailServ1.howManyMailItems("bob"));
		assertEquals(1, mailServ1.howManyMailItems("bob@mailServ1"));
	}

	public void testDirectMail()
	{
		MailServer mailServ2 = new MailServer("mailServ2", internet1);
		MailClient bob = new MailClient(mailServ1, "bob", "mailServ1");
		MailClient fred = new MailClient(mailServ2, "fred", "mailServ1");
		bob.sendMailItem("fred@mailServ1", "wasup fred");
		assertEquals(1, mailServ1.howManyMailItems("fred@mailServ1"));
	}

	public void testSendMailSame()
	{
		MailClient george = new MailClient(mailServ1, "george", "mailServ1");
		bob.sendMailItem("george@mailServ1", "hey bob");
		assertEquals(true, george.printNextMailItem());
	}

	public void testSendMailDifferent()
	{
		bob.sendMailItem("fred@mailServ2", "hey fred");
		assertEquals(true, fred.printNextMailItem());
	}
}





