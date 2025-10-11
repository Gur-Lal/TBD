# SOEN 342

### Team Members:

* **Team Lead: Gur Lal**  
  Student ID: 40284042
* **Simon Bernier**  
  Student ID: 40280792
* **Paoleno Nikyema**  
  Student ID: 40127111







\# SOEN 342 – EU Rail Network Search System



This project implements \*\*Iteration 1\*\* of the EU Rail Network system in Java.



It allows users to:

\- Load train connection data from a CSV file  

\- Search for \*\*direct\*\* and \*\*indirect (1–2 stop)\*\* trips between cities  

\- Calculate total travel time and fares (First and Second class)  

\- Sort results by \*\*duration\*\* or \*\*price\*\*  

\- Use a simple \*\*console interface\*\* to perform searches  



---



\##  Project Structure

src/

└── main/

├── java/

│ └── com/soen342/

│ ├── App.java → Main console entry point

│ ├── domain/

│ │ ├── Connection.java

│ │ ├── Parameters.java

│ │ ├── Trip.java

│ │ └── Search.java

│ └── service/

│ ├── ConnectionCatalog.java

│ ├── SearchResult.java

│ └── SearchService.java

└── resources/

└── eu\_rail\_network.csv → Dataset (train routes)





---



\##  Requirements

\- \*\*Java 17 or newer\*\* (Java 21 recommended)  

\- \*\*Apache Maven 3.6+\*\* (for building and running)



---



\##  Running the Program



\### Option 1 – Run from VS Code or IntelliJ

1\. Open the project folder in your IDE.  

2\. Ensure the file `eu\_rail\_network.csv` is located in:

src/main/resources/



3\. Open `App.java` → press \*\*Run (▶)\*\* to launch.



---



\### Option 2 – Run from Terminal with Maven

\# Navigate to your project folder





\# Compile

mvn clean compile



\# Run

mvn exec:java -Dexec.mainClass="com.soen342.App"

If Maven isn’t configured for exec:java, you can run manually:







mvn package

java -cp target/classes com.soen342.App

&nbsp;How It Works

When launched, the app:



Dynamically loads the dataset eu\_rail\_network.csv

(from src/main/resources/ or the classpath)



Lets the user search via the console for:



Departure City



Arrival City



Sorting Option (durationAsc, durationDesc, priceAsc, priceDesc, none)



Displays all matching trips (direct and indirect).









&nbsp;CSV Format

Each line in eu\_rail\_network.csv must follow:



Route ID, Departure City, Arrival City, Departure Time, Arrival Time,

Train Type, Days of Operation, 1st Class Rate, 2nd Class Rate







Concordia University — SOEN 342 (Winter 2025)

