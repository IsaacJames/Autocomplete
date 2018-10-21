###### This program was set as coursework for a first year partnered programming projects module. The code was written in my first year of study in February 2018.

# autocomplete

This program is an autocomplete implementation for a search box mock-up in Java. Complete queries are displayed for a search prefix as the user is typing the search. Search terms are weighted and displayed in descending weight order. GUI was written using Java Swing.

### Build instructions:
#### Basic:
From src folder:  *java Project1 \<search dictionary> \<max number of results displayed>*

The src folder contains the basic program. The program requires two command line arguments: a search dictionary (list of weighted search terms) and an integer denoting the maximum number of autocompleted results that can be shown at one time. The search dictionary is formatted as a list of integer/text pairs each separated by a line break. The first line should contain an integer representing the number of search terms in the dictionary. Some example dictionaries are available to view in this repository.

#### Extended:

From src_ext folder:  *java Project1Ext \<search dictionary> \<max number of results displayed>*

The src_ext folder contains an extended version of the basic program. Additional functionality includes:
+ The normalisation of diacritical marks in dictionary search terms to allow users to search for these terms using standard ASCII symbols.
+ Invalid user searches (prefixes which do not match any dictionary search terms) result in a helpful message displaying the most similar available term.
+ Autocompleted search results are interactive and if double clicked will display the selected term's weighting. This information can have contextual meaning, such as weight representing population in the cities.txt example dictionary.
+ Displayed search statistics for the number of matches found for a given prefix and the time taken to retrieve these results.
