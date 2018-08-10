# ForgeOilSpotfireData

AUTHOR: Justin Hung

ForgeOilSpotfireData is a program that reads and formats well data to output a csv file that can be read using Spotfire to do 
data analytics. 

Two input files alongside a las data directory are required to run the program to produce a formatted master file.
The two input files include a primary tops file and a general well file (GWI). An optional secondary tops file can be used
to generate formation picks within the bounds set by the primary tops file. 

## Top File: 
The tops file can either be a xlsx or csv file that contains formation picks for different depths within a well. 

*Note: csv files are more stable because large xlsx files can cause the program to run out of allocated memory space while
large csv files can not.

Both primary and secondary top file inputs can mix and match between csv or xlsx but using csv input files is 
encouraged for all input files. 

#Formatting: 

*Note: Sorting and formatting is not an issue when using Accumap's export manager with default System Tops set as the 
template. The formatting guidelines below are used for user top picks. 

The tops file needs to be sorted by the Sort UWI column from A to Z on the first level. Secondly, the TVD column needs to be
sorted from smallest to largest on the second level.

The first row of the file can include a "©2018 IHS Markit" dummy line followed by the column headers in the second row. 
The top file will still work as intended if the "©2018 IHS Markit" dummy line is not included as long as the first row 
is the column header followed by data starting in the second row. 

First column in the tops file must be Sort UWI like the following: 1W60760827160000. There can not be a single blank Sort UWI 
in this column. 
 
Second column must be the UWI column in the format: ???/??-??-???-??W?/?. There can not be a single blank UWI in this column. 
 
Fourth column must include the Formation Short Name. All letters in this column must be capitalized. Data in this column can
be blank (if blank, line is not read). 
User input formations for top and bottom formations must match the formation short name in the top file. Top file formations 
with extra spaces before or after will not be picked up by the program if the user input formation does not include those 
extra spaces. 

Sixth column must include the TVD (m) column. All data in this column must be numeric. Data in this column can
be blank (if blank, line is not read). If a stray space (' ') or work is included in the column the program will break. 

All other columns can be left blank or included because they are not read into the program. Please ensure that the columns
1, 2, 4, and 6 and populated with the right data set as specified or else the program will not run as intended.

## GWI File:
The GWI file is a general well file that can b
