// ParticleSimulator.cpp : Defines the entry point for the application.
//

#include "ParticleSimulator.h"
#include"imgui.h"
#include"imgui_impl_glfw.h"
#include"imgui_impl_opengl3.h"

#include "glad/glad.h"
#include "GLFW/glfw3.h"

#include<string>
#include<vector>
#include<algorithm>
#include<iostream>


using namespace std;

struct Sprite {
	double x;
	double y;

	Sprite(double x_val, double y_val) : x(x_val), y(y_val) {}
};

const double frameWidth = 1280;
const double frameHeight = 720;

const double spriteSize = ((frameWidth / 33.0) + (frameHeight / 19.0)) / 2.0;

const int userVelocity = 1;
Sprite user = { 20, 20 };

// TODO: Send updated user position to server and wait for updated particle list
// NOTES: Arbitrarily divided by 4.5 because it looks like it matches the backend, wats up with that tho
static void keyCallback(GLFWwindow* window, int key, int scancode, int action, int mods) {
	if (key == GLFW_KEY_W && action == GLFW_PRESS) {
		user.y -= userVelocity / 4.5;
		std::cout << "Updated User Pos: " << user.x << " " << user.y << "\n";
	}
	else if (key == GLFW_KEY_A && action == GLFW_PRESS) {
		user.x -= userVelocity / 4.5;
		std::cout << "Updated User Pos: " << user.x << " " << user.y << "\n";
	}
	else if (key == GLFW_KEY_S && action == GLFW_PRESS) {
		user.y += userVelocity / 4.5;
		std::cout << "Updated User Pos: " << user.x << " " << user.y << "\n";
	}
	else if (key == GLFW_KEY_D && action == GLFW_PRESS) {
		user.x += userVelocity / 4.5;
		std::cout << "Updated User Pos: " << user.x << " " << user.y << "\n";
	}
}

static void drawElements(std::vector<Sprite>& particles) {
	ImDrawList* drawList = ImGui::GetWindowDrawList();

	double clientUserX = frameWidth / 2;
	double clientUserY = frameHeight / 2;
	drawList->AddCircleFilled(ImVec2(clientUserX, clientUserY), spriteSize / 2.0, IM_COL32_WHITE, 32);

	for (int i = 0; i < particles.size(); i++) {
		double translatedPX = clientUserX + ((particles[i].x - user.x) * (spriteSize / 2.0));
		double translatedPY = clientUserY + ((particles[i].y - user.y) * (spriteSize / 2.0));
		/*double translatedPX = (clientUserX / user.x) * particles[0].x;
		double translatedPY = (clientUserY / user.y) * particles[0].y;*/

		/*std::cout << i << " " << translatedPX << "\n";
		std::cout << i << " " << translatedPY << "\n";*/

		drawList->AddCircleFilled(ImVec2(translatedPX, translatedPY), spriteSize / 2.0, IM_COL32(160, 32, 240, 255), 32);
	}

}

int main()
{
	// dummy particle list
	// NOTES: I dont understand why this works in making 16 cols
	// NOTES: (20, 20) and (29, 20) in backend are side by side
	// NOTES: (20, 20) and (22, 20) in client are side by side???????????
	// NOTES: It takes 9 button presses to get to next col/row in both server and client
	std::vector<Sprite> particles = {
		Sprite(1.0, 1.0),
		Sprite(user.x + 4, user.y),
		Sprite(user.x + 6, user.y),
		Sprite(user.x + 8, user.y),
		Sprite(user.x + 10, user.y),
		Sprite(user.x + 12, user.y),
		Sprite(user.x + 14, user.y),
		Sprite(user.x + 16, user.y),
		Sprite(user.x + 18, user.y),
		Sprite(user.x + 20, user.y),
		Sprite(user.x + 22, user.y),
		Sprite(user.x + 24, user.y),
		Sprite(user.x + 26, user.y),
		Sprite(user.x + 28, user.y),
		Sprite(user.x + 30, user.y),
		Sprite(user.x + 32, user.y),
	};
	std::cout << "PX " << particles[0].x << "\n";
	std::cout << "PY " << particles[0].y << "\n";

	// Initialize GLFW
	glfwInit();
	glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
	glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
	glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
	glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

	GLFWwindow* window = glfwCreateWindow(frameWidth, frameHeight, "Particle Simulator Client", NULL, NULL);
	// Error check if the window fails to create
	if (window == NULL)
	{
		std::cout << "Failed to create GLFW window" << std::endl;
		glfwTerminate();
		return -1;
	}
	// Introduce the window into the current context
	glfwMakeContextCurrent(window);

	//Load GLAD so it configures OpenGL
	gladLoadGL();
	glViewport(0, 0, frameWidth, frameHeight);

	// ImGui init
	IMGUI_CHECKVERSION();
	ImGui::CreateContext();
	ImGuiIO& io = ImGui::GetIO(); (void)io;
	ImGui::StyleColorsDark();
	ImGui_ImplGlfw_InitForOpenGL(window, true);
	ImGui_ImplOpenGL3_Init("#version 330");

	glfwSetKeyCallback(window, keyCallback);

	// Main while loop
	while (!glfwWindowShouldClose(window))
	{
		// Background color
		glClearColor(0.07f, 0.13f, 0.17f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT);

		ImGui_ImplOpenGL3_NewFrame();
		ImGui_ImplGlfw_NewFrame();
		ImGui::NewFrame();

		// ImGUI window creation
		ImGui::SetNextWindowPos(ImVec2(0.0, 0.0));
		ImGui::SetNextWindowSize(ImVec2(frameWidth, frameHeight));

		ImGui::Begin("Title", NULL, ImGuiWindowFlags_NoResize | ImGuiWindowFlags_NoTitleBar);
		ImGui::Text("Current FPS: %.3f", io.Framerate);
		drawElements(particles);
		ImGui::End();

		// Renders the ImGUI elements
		ImGui::Render();
		ImGui_ImplOpenGL3_RenderDrawData(ImGui::GetDrawData());

		// Swap the back buffer with the front buffer
		glfwSwapBuffers(window);

		// Take care of all GLFW events
		glfwPollEvents();
	}

	// Deletes all ImGUI instances
	ImGui_ImplOpenGL3_Shutdown();
	ImGui_ImplGlfw_Shutdown();
	ImGui::DestroyContext();
	glfwDestroyWindow(window);
	glfwTerminate();
	return 0;
}
