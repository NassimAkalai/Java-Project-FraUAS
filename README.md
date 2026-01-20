# Java-Project-FraUAS
This project was developed as part of the OOP-Java module. It implements a small user interface created under a limited time frame. The application retrieves data from a private API, which requires a valid private access token. The data includes information about drones, such as their speed. Dynamic updates of the data are also taken into account.


Java Project for the module 'Advanced Object Oriented Programming' - Java @FraUAS

If you want to use this code you need to install the FortiClient VPN via: https://confluence.frankfurt-university.de/display/SN1/Anleitungen.  
You also need to have an active account on the FraUAS CampUAS (moodle) to access the VPN.                                
Furthermore, you also need to be in posession of a valid token which is needed to access the API.                                        
If you want to access the API itself you will need a valid username and password.                                                        
If you want to execute the jar file via your system terminal, type in following command (while inside the directory in which you have saved the file): java -jar FinalJavaProject.jar

### Change Log

### [Version 0.4.0] - 2025-02-04
- Implemented a paging system for drone dynamics
- DynamicsCalculator class created to calculate the average speed and total distance of ten instances of a specific drone
- Added a first and last page feature within drone dynamics
  
### [Version 0.3.3] - 2025-02-04
- ErrorLogger class has been implemented to throw error messages towards the user

### [Version 0.3.2] - 2025-02-03
- Added logging system (LoggerFactory)
- GUI has been finalized
- No console prints, only logging
- Exceptions and errors are shown in a small error panel
- Token and URLs can be added/ removed/ adjusted through a config file
- Added collections for data

### [Version 0.3.1] - 2025-02-02
- Fixed some bugs
- GUI has been finished
- Output for Drone Dynamics has been achieved

### [Version 0.3.0] - 2025-01-28
- Drone ID range for dynamics is now dynamic since drone IDs can be changed
- Added an ID fetcher class which fetches the current first and last drone ID and sets the interval of valid drones
- Fixed timestamp output

### [Version 0.2.3] - 2025-01-24
- Backend is nearly finished
- Information is fetched correctly
- Paging System now has a 'back' option which leads to the main menu
- Increased efficiency by fetching dynamic data only when 'count' changes, timer now checks for changes in 'count' first 

### [Version 0.2.2] - 2025-01-24
- Fixed issues with loading newest data (drone dynamics)
- Fixed drone type info showing up as a link (drones)  

### [Version 0.2.1] - 2025-01-24
- Mixed the two approaches
- Added an interface for managing drone (info) selection
- Added an abstract class for drone dynamics details (nested jsons)
- Added corrected paging system for drones and drone types
- Added hashmaps to store drones and drone info options
- Added format data and format link classes to get better time output and to control the URLs
- Solution for fetching newest drone data (drone dynamics)
- Fixed the timer thread for multiple threading

### [Version 0.2.0] - 2025-01-17
- Reached Milestone 2
- Still two approaches
- Temporary GUI to showcase functionality
- Currently four classes: Main, Drones, DroneTypes and DroneDynamics

### [Version 0.1.2] - 2025-01-14
- Two different approaches
- Created Main class for executing all other classes

### [Version 0.1.1] - 2025-01-13
- Added paging system for drones and drone types
- Implemented simple ui designs, will be reworked later

### [Version 0.1.0] - 2025-01-11
- Code for drone dynamics nearly finished
- Currently updates by second
- Nested info available under 'additioal information'
- Fetched nested info inside nested info
- Fixed some issues

### [Version 0.0.3] - 2025-01-10
- Created a code for the drone types api
- Added a sorting system for sorted output

### [Version 0.0.2] - 2025-01-05
- Modified HelloRest code
- Added notations for better understanding
- Fixed Connection error

### [Version 0.0.1] - 2024-12-27
- Rough code
- Not able to connect to the server
