# CMakeList.txt : CMake project for ParticleSimulator, include source and define
# project specific logic here.
#
cmake_minimum_required (VERSION 3.8)
find_package(OpenGL REQUIRED)


# Add source to this project's executable.
add_executable (ParticleSimulator "ParticleSimulator.cpp" "glad.c")

target_include_directories(${PROJECT_NAME} PRIVATE
	${PROJECT_SOURCE_DIR}/ParticleSimulator/imgui
	${PROJECT_SOURCE_DIR}/ParticleSimulator/Libraries/include
)

target_link_libraries(${PROJECT_NAME} PRIVATE 
    OpenGL::GL 
    ${PROJECT_SOURCE_DIR}/ParticleSimulator/Libraries/lib/glfw3.lib
)

target_sources(${PROJECT_NAME} PRIVATE 
    ${PROJECT_SOURCE_DIR}/ParticleSimulator/imgui/imgui.cpp
    ${PROJECT_SOURCE_DIR}/ParticleSimulator/imgui/imgui_demo.cpp
    ${PROJECT_SOURCE_DIR}/ParticleSimulator/imgui/imgui_draw.cpp
    ${PROJECT_SOURCE_DIR}/ParticleSimulator/imgui/imgui_tables.cpp
    ${PROJECT_SOURCE_DIR}/ParticleSimulator/imgui/imgui_impl_glfw.cpp
    ${PROJECT_SOURCE_DIR}/ParticleSimulator/imgui/imgui_impl_opengl3.cpp
    ${PROJECT_SOURCE_DIR}/ParticleSimulator/imgui/imgui_widgets.cpp
)
message(${PROJECT_SOURCE_DIR}/ParticleSimulator/include)
# TODO: Add tests and install targets if needed.
