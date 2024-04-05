TDISCM Problem Set 3 - Problem Set #3: Distributed System Implementation
Samuel Derrick Nieva, Viktoria Lila Sicuan (S12)

Description
This Java program serves as a particle physics simulator with border-bouncing capabilities. It allows users to visualize and simulate the behavior of particles within a defined environment, observing their interactions and collisions with borders.

How to Run
To run the program, follow these steps:

SERVER:
Navigate to the following file path: PROJECT_FOLDER/Server/src
Once in the folder, execute the following command in the terminal or command prompt:
javac -cp Server/json-20140107.jar Server/*.java
After successful compilation, run the program by executing:
java -cp .;Server/json-20140107.jar Server/Main
This will launch the GUI interface where you can interact with the particle simulator.

Alternatively, you can run the program using IntelliJ IDEA:

Open the project in IntelliJ IDEA.
Build the application.
Run the Server.Main.java file.
Usage
Once the program is running, you can:

Define the initial parameters of the particle generation.
Observe the behavior of particles as they move within the defined environment.
Visualize collisions between particles and borders.
Customize the properties of particles and the simulation environment.

CLIENT:
In Visual Studio (The group used Community 2019), go to File -> Open -> CMake, then navigate to the project folder's root directory and open the CMakeLists.txt file found in it.
Afterwards, open the CMake Targets View using Visual Studio's Solution Explorer
![image](https://github.com/viktoria-ls/Particle-Simulator-Distributed-System/assets/104417570/4e51cfd0-5448-45aa-b872-c2162d7f2bd2)
Once in, you may select ParticleSimulator.exe as the debug target and run it that way
![image](https://github.com/viktoria-ls/Particle-Simulator-Distributed-System/assets/104417570/9f04c587-5407-42d9-a268-e48ec090180b)

