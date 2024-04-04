﻿// ParticleSimulator.cpp : Defines the entry point for the application.
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
#include <cmath>


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
Sprite user = { 1280, 720 };

// TODO: Send updated user position to server and wait for updated particle list
static void keyCallback(GLFWwindow* window, int key, int scancode, int action, int mods) {
	if (key == GLFW_KEY_W && action == GLFW_PRESS) {
		if (user.y + userVelocity > 720)
			user.y = 720.0;
		else
			user.y += userVelocity;
		std::cout << "Updated User Pos: " << user.x << " " << user.y << "\n";
	}
	else if (key == GLFW_KEY_A && action == GLFW_PRESS) {
		if (user.x - userVelocity < 0)
			user.x = 0.00;
		else
			user.x -= userVelocity;
		std::cout << "Updated User Pos: " << user.x << " " << user.y << "\n";
	}
	else if (key == GLFW_KEY_S && action == GLFW_PRESS) {
		if (user.y - userVelocity < 0)
			user.y = 0.0;
		else
			user.y -= userVelocity;
		std::cout << "Updated User Pos: " << user.x << " " << user.y << "\n";
	}
	else if (key == GLFW_KEY_D && action == GLFW_PRESS) {
		if (user.x + userVelocity > 128)
			user.x = 1280.00;
		else
			user.x += userVelocity;
		std::cout << "Updated User Pos: " << user.x << " " << user.y << "\n";
	}

	
}

static void drawElements(std::vector<Sprite>& particles) {
	ImDrawList* drawList = ImGui::GetWindowDrawList();
	//Translation Vector
	double xVector = 148.5 - user.x;
	double yVector = 85.5 - user.y;

	double clientUserX = 1280 * ((148.5)/297);
	double clientUserY = 720 * ((85.5)/171);

	drawList->AddCircleFilled(ImVec2(clientUserX, clientUserY), spriteSize / 2.0, IM_COL32_WHITE, 32);

	for (int i = 0; i < particles.size(); i++) {
		double translatedPX = particles[i].x + xVector;
		double translatedPY = particles[i].y + yVector;

		translatedPX = 1280 * (translatedPX / 297);
		translatedPY = 720 - (720 * (translatedPY / 171));

		drawList->AddCircleFilled(ImVec2(translatedPX, translatedPY), spriteSize / 2.0, IM_COL32(160, 32, 240, 255), 32);
	}

	ImVec2 topBorderEndPoint = ImVec2(1280, 360 - spriteSize / 2.0 - 720 + user.y);
	ImVec2 leftBorderEndPoint = ImVec2(640 - spriteSize / 2.0 - user.x, 720);
	ImVec2 bottomBorderEndPoint = ImVec2(0, 360 + spriteSize / 2.0 + user.y);
	ImVec2 rightBorderEndPoint = ImVec2(640 + spriteSize / 2.0 + (1280 - user.x), 0);
	
	drawList->AddRectFilled(ImVec2(0, 0), topBorderEndPoint, IM_COL32(0, 0, 500, 255));				// Draws top border
	drawList->AddRectFilled(ImVec2(0, 0), leftBorderEndPoint, IM_COL32(0, 0, 500, 255));			// Draws left border
	drawList->AddRectFilled(ImVec2(1280, 720), bottomBorderEndPoint, IM_COL32(0, 0, 500, 255));		// Draws bottom border
	drawList->AddRectFilled(ImVec2(1280, 720), rightBorderEndPoint, IM_COL32(0, 0, 500, 255));		// Draws right border
}

int main()
{
	// dummy particle list
	std::vector<Sprite> particles = {
		Sprite(20, 700)
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
