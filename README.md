# File-Traversal-System
 A Java program that recursively traverses a file system, processes text files, and generates summaries based on specific criteria.

Recursive File System Traversal:

A Java program that recursively traverses a file system, processes text files, and generates summaries based on specific criteria.

Key Features:

Implement a recursive file system traversal method that finds all files matching a user-specified name.

Make sure to ask the user for the file name to search for.

Display the full paths (absolute path) of all matching files found during the traversal.

Write out full paths of all the matching files to "search_results.txt" in the following format:

Absolute Path of File (such as /Users/harrypotter/Documents/Prisoner_of_Azkaban.txt) 

Implement a recursive traversal to focus on text files (identified by the .txt extension).

Generate a summary of the text files, including:

Total number of text files found

Average size of all text files in the current folder and all its subfolders

Write the results to a file named "results.txt" in the following format:

[File Name], [File Size in bytes]

Use the generated "results.txt" file to verify the correctness of your average size computation.

Enhance the text file summary to include:

Name of the shortest text file (by size) - Display this to the console (you do not need to write this to a file)

Name of the largest text file (by size)  - Display this to the console (you do not need to write this to a file)

Implement a queue using a linked list data structure to store references to all text files encountered during traversal.

Process the files from the queue one at a time to generate the summary.

For each text file processed, create a TextFile object and add it to the queue.

Create a TextFile class with the following attributes:

File name

File size (in bytes)

Absolute path of the file

Write the results to a file named "results.txt" in the following format: 

[File Name], [Absolute Path], [File Size in bytes]


Instructions:

Follow prompts for user inputs.

Give argument for file directory 
