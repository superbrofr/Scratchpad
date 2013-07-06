using System.Collections.Generic;
using System.IO;
using System.Diagnostics;

// <summary>
// Extracts the fan-in details from a file containing fan-out details. Fan-out file must be in the format:
// <class type>,<class name>,<fan out count>,<comma separated list of dependent class names>
// Program requires two parameters - the input file and a name for the output file. Fan-n details is written to the 
// specified file in the following format:
// <class type>,<class name>,<fan in count>,<comma-separated list of used class names>
 
// Author: Charlotte Pierce [7182139]
// Version: 1.0 - Last modified 15/8/2011
// </summary>
public class ExtractFanIn
{
	TextReader inFile;
	TextWriter outFile;
	Dictionary<string, ClassInfo> classes; //stores the classInfo object, keyed to the relevant class name
	List<string> classNames;
	int maxFanOut;
	int maxFanIn;
	int sourceNodes;
	int sinkNodes;
	
	// <summary>
  // Validates the parameters passed to the program - the program is run if they are valid, otherwise
  // the program is terminated.
  // </summary>
	public static void Main(string[] args)
	{
		try
		{
			if(args.Length == 2){
				new ExtractFanIn(new StreamReader(args[0]), args[1]).run();
			}
			else{
				System.Console.WriteLine("ERROR: Invalid number of arguments.");
				System.Environment.Exit(1);
			}
		}
		catch(FileNotFoundException)
		{
			System.Console.WriteLine("ERROR: Invalid input file.");
			System.Environment.Exit(1);
		}
	}
	
  // <summary>
  // Creates an instance of the extraction program and opens the output file to prepare for writing.
  // <param name="inputFile">The reader for the fan-out details.</param>
  // <param name="outputFileName">The provided name output data file.</param>
  // </summary>
	public ExtractFanIn(TextReader inputFile, string outputFileName)
	{
		inFile = inputFile;
		outFile = new StreamWriter(outputFileName);
		classNames = new List<string>();
		classes = new Dictionary<string, ClassInfo>();
		maxFanOut = 0;
		maxFanIn = 0;
		sourceNodes = 0;
		sinkNodes = 0;
	}
	
	// <summary>
  // Runs the extractor.
  // 1. Reads the input file, generating a ClassInfo object for each unique class, and storing it in
    // 'classes', associated with the class name. 
  // 2. Scans each class found, adding the fan in details gained from the fan-out classes.
  // 3. Creates the results file.
  // 4. Prints results to the console.
  // </summary>
	public void run()
	{
		long lineCount = 0;
		Stopwatch timer = new Stopwatch();
		timer.Start();
		string nextLine;
		while((nextLine = inFile.ReadLine()) != null)
		{
			++lineCount;
			processLine(nextLine);
		}
		generateFanInData();
		printResultsFile();
		
		timer.Stop();
		printResultsConsole(timer.ElapsedMilliseconds, lineCount);
		
		inFile.Close();
		outFile.Close();
	}
	
	// <summary>
  // Takes the class information read from the input file and adds the fan-in details for each class.
  // </summary>
	private void generateFanInData()
	{
		foreach(string s in classNames)
		{
			ClassInfo clInfo = classes[s];
			List<string> fanOut = clInfo.FanOut;
			int fanOutCnt = fanOut.Count;
			if(fanOutCnt > maxFanOut)
				maxFanOut = fanOutCnt;
			if(fanOutCnt == 0)
				++sinkNodes;
				
			foreach(string fan in fanOut)
			{
				ClassInfo fanClInfo = classes[fan];
				fanClInfo.addFanIn(s);
			}
		}
	}
	
	// <summary>
  // Prints the results of the extraction to the specified output file. Finds the max fan-in and source node count
  // while reading the data.
  // </summary>
	private void printResultsFile()
	{
		foreach(string s in classNames)
		{
			ClassInfo clInfo = classes[s];
			List<string> fanIn = clInfo.FanIn;
			int fanInCnt = fanIn.Count;
			if(fanInCnt > maxFanIn)
				maxFanIn = fanInCnt;
			if(fanInCnt == 0)
				++sourceNodes;
				
			outFile.Write(clInfo.Type + "," + s + "," + fanInCnt);
			foreach(string fan in fanIn)
				outFile.Write("," + fan);
			outFile.Write(System.Environment.NewLine);
		}
	}

	// <summary>
  // Prints the results of the extraction to the console.
  // <param name="timeElapsed">The number of ms taken.</param>
  // <param name="lineCount">The number of lines read from the input file.</param>
  // </summary>
	private void printResultsConsole(long timeElapsed, long lineCount)
	{
		System.Console.WriteLine("Processed " + lineCount + " lines in " + timeElapsed + "ms");
		System.Console.WriteLine("#Source nodes: " + sourceNodes);
		System.Console.WriteLine("#Sink nodes: " + sinkNodes);
		System.Console.WriteLine("Max Fan-Out: " + maxFanOut);
		System.Console.WriteLine("Max Fan-In: " + maxFanIn);
	}
	
	// <summary>
  // Processes a single line from the input file. Assumes the following format is followed in the input file:
  // <class type>,<class name>,<fan out count>,<comma separated list of dependent class names>.
  // </summary>
	private void processLine(string aLine)
	{
		string[] lineSplit = aLine.Split(',');
		int fanOutCnt;
		if((lineSplit.Length >= 3) && (int.TryParse(lineSplit[2], out fanOutCnt)))
		{
			string clName = lineSplit[1];
			string clType = lineSplit[0];
			//check there is enough fan-out data on the line
				if((fanOutCnt + 3) != lineSplit.Length){
					System.Console.WriteLine("ERROR: Incorrect format found in input file.");
					System.Environment.Exit(1);
				}
			
			if(!classNames.Contains(clName))
			{
				ClassInfo newInfo = new ClassInfo(clType);
				classNames.Add(clName);
				classes.Add(clName, newInfo);
				
				if(fanOutCnt > 0)
				{
					for(int i = 3; i != lineSplit.Length; ++i){
						newInfo.addFanOut(lineSplit[i]);
					}
				}
			}
		}
		else
		{
			System.Console.WriteLine("ERROR: Input file in wrong format.");
			System.Environment.Exit(1);
		}
	}
}