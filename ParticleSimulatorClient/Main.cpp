#include"imgui.h"
#include"imgui_impl_glfw.h"
#include"imgui_impl_opengl3.h"

#include<string>
#include<vector>
#include <algorithm>
#include<iostream>
#include<glad/glad.h>
#include<GLFW/glfw3.h>

struct Sprite {
	double x = 400;
	double y = 400;
};

const int windowWidth = 800;
const int windowHeight = 800;

const double frameWidth = 1280;
const double frameHeight = 720;

const double spriteSize = std::min((frameWidth / 33.0), (frameHeight / 19.0));

const int userVelocity = 10;
Sprite user = {50, 50};

// TODO: Send direction (and speed?) to server so it can 
static void keyCallback(GLFWwindow* window, int key, int scancode, int action, int mods) {
	if (key == GLFW_KEY_W && action == GLFW_PRESS) {
		std::cout << "Pressed W";
		user.y -= userVelocity;
	}
	else if (key == GLFW_KEY_A && action == GLFW_PRESS) {
		std::cout << "Pressed A";
		user.x -= userVelocity;
	}
	else if (key == GLFW_KEY_S && action == GLFW_PRESS) {
		std::cout << "Pressed S";
		user.y += userVelocity;
	}
	else if (key == GLFW_KEY_D && action == GLFW_PRESS) {
		std::cout << "Pressed D";
		user.x += userVelocity;
	}
}

static void drawElements(std::vector<Sprite> &particles, float frameWindowWidth, float frameWindowHeight) {
	ImDrawList* drawList = ImGui::GetWindowDrawList();

	double clientUserX = frameWindowWidth + (frameWidth / 2);
	double clientUserY = frameWindowHeight + (frameHeight / 2);
	drawList->AddCircleFilled(ImVec2(clientUserX, clientUserY), spriteSize/2.0, IM_COL32_WHITE, 32);

	/*double translatedPX = clientUserX + ((particles[0].x - user.x) * spriteSize / 2.0);
	double translatedPY = clientUserY + ((particles[0].y - user.y) * spriteSize / 2.0);*/
	double translatedPX = (clientUserX / user.x) * particles[0].x;
	double translatedPY = (clientUserY / user.y) * particles[0].y;
	drawList->AddCircleFilled(ImVec2(translatedPX, translatedPY), spriteSize/2.0, IM_COL32(160, 32, 240, 255), 32);

	std::cout << "gorp\n";
	std::cout << clientUserX << "\n";
	std::cout << clientUserY << "\n";
	std::cout << translatedPX << "\n";
	std::cout << translatedPY << "\n";

}

int main()
{
	// dummy particle list
	std::vector<Sprite> particles = {Sprite(), Sprite(), Sprite(), Sprite(), Sprite()};
	particles[0].x = 70;
	particles[0].y = 50;

	// Initialize GLFW
	glfwInit();
	glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
	glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
	glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
	glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
	glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

	GLFWwindow* window = glfwCreateWindow(windowWidth, windowHeight, "Particle Simulator Client", NULL, NULL);
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
	glViewport(0, 0, windowWidth, windowHeight);

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

		// Calculate position for the center of the main window and imgui window
		float mainWindowCenterX = ImGui::GetIO().DisplaySize.x / 2.0f;
		float mainWindowCenterY = ImGui::GetIO().DisplaySize.y / 2.0f;
		float windowInFrameCenterX = mainWindowCenterX - (frameWidth / 2.0f);
		float windowInFrameCenterY = mainWindowCenterY - (frameHeight / 2.0f);

		// ImGUI window creation
		ImGui::SetNextWindowPos(ImVec2(windowInFrameCenterX, windowInFrameCenterY));
		ImGui::SetNextWindowSize(ImVec2(frameWidth, frameHeight));

		ImGui::Begin("Title", NULL, ImGuiWindowFlags_NoResize | ImGuiWindowFlags_NoTitleBar);
		ImGui::Text("Current FPS: %.3f", io.Framerate);
		drawElements(particles, windowInFrameCenterX, windowInFrameCenterY);
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