# ForgeOilSpotfireData

AUTHOR: Justin Hung

Four input files are required to run the program and produce a formatted master file for the data. 

~Top File: 
Top file should be an xlsx file
First column in the excel file should be a UWI formatted as ???/??-??-???-??W?/?. Note: SORT UWI should not be used in first column.
Second column does not matter (usually KB elevation)
Third column contains the formation. If VKNG, VKNG UPPR, VKNS, is the zone of interest with a 5 meter buffer. Make sure tops are listed in
the following order from top to bottom: VKNG UPPR, VKNG, VKNS, JLFU. (note BFS is not included but JLFU is because the program needs to 
know where to start the bottom buffer region. 
Fourth column contains TVD values for the formation.
All other columns can be left blank or included. As long as the first, third and fourth columm is populated.

~
