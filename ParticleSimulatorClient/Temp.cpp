#include"imgui.h"
#include"imgui_impl_glfw.h"
#include"imgui_impl_opengl3.h"

#include<string>
#include<iostream>
#include<glad/glad.h>
#include<GLFW/glfw3.h>

struct User {
	double x = 400;
	double y = 400;
	double velocity = 10;

};

const int windowWidth = 800;
const int windowHeight = 800;
const int frameWidth = 1280;
const int frameHeight = 720;

User user;

static void keyCallback(GLFWwindow* window, int key, int scancode, int action, int mods) {
	if (key == GLFW_KEY_W && action == GLFW_PRESS) {
		user.y -= user.velocity;
	}
	else if (key == GLFW_KEY_A && action == GLFW_PRESS) {
		user.x -= user.velocity;
	}
	else if (key == GLFW_KEY_S && action == GLFW_PRESS) {
		user.y += user.velocity;
	}
	else if (key == GLFW_KEY_D && action == GLFW_PRESS) {
		user.x += user.velocity;
	}
}

int main()
{
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
		// Specify the color of the background
		glClearColor(0.07f, 0.13f, 0.17f, 1.0f);
		// Clean the back buffer and assign the new color to it
		glClear(GL_COLOR_BUFFER_BIT);

		ImGui_ImplOpenGL3_NewFrame();
		ImGui_ImplGlfw_NewFrame();
		ImGui::NewFrame();

		// Calculate position for the center of the main window
		float mainWindowCenterX = ImGui::GetIO().DisplaySize.x / 2.0f;
		float mainWindowCenterY = ImGui::GetIO().DisplaySize.y / 2.0f;

		// Calculate position for the center of the window in the frame
		float windowInFrameCenterX = mainWindowCenterX - (frameWidth / 2.0f);
		float windowInFrameCenterY = mainWindowCenterY - (frameHeight / 2.0f);

		// ImGUI window creation
		ImGui::SetNextWindowPos(ImVec2(windowInFrameCenterX, windowInFrameCenterY));
		ImGui::SetNextWindowSize(ImVec2(frameWidth, frameHeight));

		ImGui::Begin("Title", NULL, ImGuiWindowFlags_NoResize | ImGuiWindowFlags_NoTitleBar);

		ImDrawList* drawList = ImGui::GetWindowDrawList();
		drawList->AddCircleFilled(ImVec2(user.x, user.y), 4.5f, IM_COL32_WHITE, 8);

		// Text that appears in the window
		//ImGui::Text("Current FPS: %.3f", io.Framerate);
		// Ends the window
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

	// Delete window before ending the program
	glfwDestroyWindow(window);
	// Terminate GLFW before ending the program
	glfwTerminate();
	return 0;
}