import java.io.*;
import java.util.HashMap;
import java.util.ArrayList;

/**
* Extracts the fan-in details from a file containing fan-out details. Fan-out file must be in the format:
* <class type>,<class name>,<fan out count>,<comma separated list of dependent class names>
* Program requires two parameters - the input file and a name for the output file. Fan-in details is written to the
* specified file in the following format:
* <class type>,<class name>,<fan in count>,<comma-separated list of used class names>
*
* @author Charlotte Pierce [7182139]
* @version 1.0 - Last modified 15/8/2011
*/
public class ExtractFanIn
{
  BufferedReader in;
  BufferedWriter out;
  HashMap<String, ClassInfo> classes;
  ArrayList<String> classNames;
  int maxFanOut;
  int maxFanIn;
  int sourceNodes;
  int sinkNodes;

  /**
  * Validates the parameters passed to the program - the program is run if they are valid, otherwise
  * the program is terminated.
  */
  public static void main(String[] args)
  {
    //check correct number of arguments - ant adds an extra argument, compensate for this
    try
    {
      //two arguments - was run with java
      if(args.length == 2){
        new ExtractFanIn(new BufferedReader(new FileReader(args[0])), args[1]).run();
      }
      //three arguments - was run with ant
      else if(args.length == 3){
        new ExtractFanIn(new BufferedReader(new FileReader(args[1])), args[2]).run();
      }
      else{
        System.out.println("ERROR: Invalid number of arguments.");
        System.exit(1);
      }
    }
    catch(IOException e)
    {
      System.out.println("ERROR: Unable to open input file.");
      System.exit(1);
    }
  }

  /**
  * Creates an instance of the extraction program and opens the output file to prepare for writing.
  * @param inputFile The reader for the fan-out details.
  * @param outputFileName The provided name output data file.
  */
  public ExtractFanIn(BufferedReader inputFile, String outputFileName)
  {
    try
    {
      in = inputFile;
      out = new BufferedWriter(new FileWriter(outputFileName));
      classes = new HashMap<String, ClassInfo>();
      classNames = new ArrayList<String>();
      maxFanOut = 0;
      maxFanIn = 0;
      sourceNodes = 0;
      sinkNodes = 0;
    }
    catch(IOException e)
    {
      System.out.println("ERROR: Unable to create specified output file.");
    }
  }

  /**
  * Runs the extractor.
  * 1. Reads the input file, generating a ClassInfo object for each unique class, and storing it in
  *   'classes', associated with the class name.
  * 2. Scans each class found, adding the fan in details gained from the fan-out classes.
  * 3. Creates the results file.
  * 4. Prints results to the console.
  */
  public void run()
  {
    try
    {
      long lineCount = 0;
      long startTime = System.currentTimeMillis();
      while(in.ready())
      {
        ++lineCount;
        processLine(in.readLine());
      }
      generateFanInData();
      printResultsFile();

			long endTime = System.currentTimeMillis();
      printResultsConsole((endTime - startTime), lineCount);

      in.close();
      out.close();
    }
    catch(IOException e)
    {
      System.out.println("ERROR: Unable to read properly from input file.");
      System.exit(1);
    }
  }

  /**
  * Takes the class information read from the input file and adds the fan-in details for each class.
  */
  private void generateFanInData()
  {
    for(String s: classNames)
    {
      ClassInfo clInfo = classes.get(s);
      ArrayList<String> fanOut = clInfo.getFanOut();
      int fanOutCnt = fanOut.size();
      if(fanOutCnt > maxFanOut)
        maxFanOut = fanOut.size();
      if(fanOutCnt == 0)
        ++sinkNodes;

      for(String fan: fanOut)
      {
        ClassInfo fanClInfo = classes.get(fan);
        fanClInfo.addFanIn(s);
      }
    }
  }

  /**
  * Prints the results of the extraction to the specified output file. Finds the max fan-in and source node count
  * while reading the data.
  */
  private void printResultsFile()
  {
    try
    {
      for(String s: classNames)
      {
        ClassInfo clInfo = classes.get(s);
        ArrayList<String> fanIn = clInfo.getFanIn();
        int fanInCnt = fanIn.size();
        if(fanInCnt > maxFanIn)
          maxFanIn = fanInCnt;
        if(fanInCnt == 0)
          ++sourceNodes;

        out.write(clInfo.getClassType() + "," + s + "," + fanInCnt);
        for(String fan: fanIn)
          out.write("," + fan);
        out.newLine();
      }
    }
    catch(IOException e)
    {
      System.out.println("ERROR: Unable to write to output file.");
      System.exit(1);
    }
  }

  /**
  * Prints the results of the extraction to the console.
  * @param timeElapsed The number of ms taken.
  * @param lineCount The number of lines read from the input file.
  */
  private void printResultsConsole(long timeElapsed, long lineCount)
  {
    System.out.println("Processed " + lineCount + " lines in " + timeElapsed + "ms");
    System.out.println("#Source nodes: " + sourceNodes);
    System.out.println("#Sink nodes: " + sinkNodes);
    System.out.println("Max Fan-Out: " + maxFanOut);
    System.out.println("Max Fan-In: " + maxFanIn);
  }

  /**
  * Processes a single line from the input file. Assumes the following format is followed in the input file:
  * <class type>,<class name>,<fan out count>,<comma separated list of dependent class names>.
  */
  private void processLine(String aLine)
  {
		try
		{
			String[] lineSplit = aLine.split(",");

			if(lineSplit.length >= 3)
			{
				String clName = lineSplit[1];
				String clType = lineSplit[0];
				int fanOutCnt = Integer.parseInt(lineSplit[2]);
				//check there is enough fan-out data on the line
				if((fanOutCnt + 3) != lineSplit.length){
					System.out.println("ERROR: Incorrect format found in input file.");
					System.exit(1);
				}

				//create the class if it doesn't already exist
				if(classes.get(clName) == null)
				{
					ClassInfo newInfo = new ClassInfo(clType);
					classNames.add(clName);
					classes.put(clName, newInfo);

					//add its fanout details
					if(fanOutCnt > 0)
					{
						for(int i = 3; i != lineSplit.length; ++i){
							newInfo.addFanOut(lineSplit[i]);
						}
					}
				}
			}
			else
			{
				System.out.println("ERROR: Input file in wrong format.");
				System.exit(1);
			}
		}
		catch(NumberFormatException e)
		{
			System.out.println("ERROR: Input file in wrong format.");
			System.exit(1);
		}
  }
}