

/**
 * The test class PictureTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class PictureTest extends junit.framework.TestCase
{
	private Circle circle1;
	private Circle circle2;

    /**
     * Default constructor for test class PictureTest
     */
    public PictureTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    protected void setUp()
    {

	}

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    protected void tearDown()
    {
    }

	public void testPic()
	{
		Picture picture1 = new Picture();
		picture1.draw();
		picture1.setBlackAndWhite();
		picture1.setColor();
	}
}

