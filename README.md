# API Project With SQlite
## Project Description
This project uses an OMDB API to search for movies and stores them locally with a sqlite database.
In the database you can search for a specific movies sorted by title, year, director and genre. You also have the options to 
add, delete, and update movies.

## Dependencies
This project needs the following dependencies:  
Maven: com.github.gotson:sqlite-jdbc:3.32.3.8                  
Maven: org.json:json:20230227


### Menu options:
1. Search for a movie - This option will search for a movie in the database. If the movie is not found in the database it will search for it in the api and add it to the database.
2. Show search history - This option will show the search history of the database. The search history can either be filtered by the next options that appear(1-5) or print everything sorted by title. 

- 2.1 Title - This option will filter the search history by title.
- 2.2 Year - This option will filter the search history by year.
- 2.3 Genre - This option will filter the search history by genre.
- 2.4 Actors - This option will filter the search history by actors.
- 2.5 Director - This option will filter the search history by director.
- 2.6 Print all - This option will print all the search history sorted by title.
3. Delete search history - This option will delete everything in the database.
4. Info - This option will print out the info about these previous options.
5. Exit - This option will exit the program.




