import numpy as np
import cv2
import os
from tkinter import filedialog, Tk

# ============ GLOBAL VARIABLES ============
# Drawing state variables
drawing = False  # Tracks if we're currently drawing
start_x, start_y = -1, -1  # Starting coordinates for shapes
color = (255, 0, 0)  # Default color (Blue in BGR format)
thickness = 2  # Line/shape thickness
fill_mode = False  # Whether to fill shapes
text = ""  # Store text input
font = cv2.FONT_HERSHEY_SIMPLEX  # Font style for text
font_scale = 1  # Text size
grid_on = False  # Grid overlay toggle
canvas_size = (800, 600)  # Width x Height of canvas

# Available tools and styles
mode = "freehand"  # Default drawing mode
modes = ["circle", "rectangle", "line", "freehand", "triangle", "text", "eraser", "spray"]
brush_styles = ["solid", "dashed", "dotted"]
current_brush = 0

# Layer system
layers = [np.ones((canvas_size[1], canvas_size[0], 3)) * 255]  # Start with one white layer
current_layer = 0  # Index of active layer

# Undo/Redo system
history = []  # Stores previous states for undo
redo_stack = []  # Stores undone states for redo
MAX_HISTORY = 20  # Maximum number of states to remember

# ============ HELPER FUNCTIONS ============
def save_state():
    """Saves current layer state for undo/redo functionality"""
    global history, redo_stack
    history.append(layers[current_layer].copy())
    if len(history) > MAX_HISTORY:
        history.pop(0)  # Remove oldest state if exceeding max history
    redo_stack.clear()  # Clear redo stack when new action is performed

def draw_grid(img):
    """Draws a grid overlay on the image"""
    grid_img = img.copy()
    # Draw vertical lines
    for x in range(0, canvas_size[0], 50):
        cv2.line(grid_img, (x, 0), (x, canvas_size[1]), (200,200,200), 1)
    # Draw horizontal lines
    for y in range(0, canvas_size[1], 50):
        cv2.line(grid_img, (0, y), (canvas_size[0], y), (200,200,200), 1)
    return grid_img

# ============ MAIN DRAWING FUNCTION ============
def draw(event, x, y, flags, param):
    """Handles all drawing operations based on mouse events"""
    global drawing, start_x, start_y, layers, current_layer, text, color

    # Handle mouse button press
    if event == cv2.EVENT_LBUTTONDOWN:
        drawing = True
        start_x, start_y = x, y
        
        # Handle text input
        if mode == "text":
            cv2.putText(layers[current_layer], text, (x, y), font, font_scale, color, thickness)
            save_state()
            text = ""

    # Handle mouse movement
    elif event == cv2.EVENT_MOUSEMOVE:
        if drawing:
            current_img = layers[current_layer]
            
            # Continuous drawing tools
            if mode == "freehand":
                cv2.line(current_img, (start_x, start_y), (x, y), color, thickness)
                start_x, start_y = x, y
            
            elif mode == "eraser":
                cv2.circle(current_img, (x, y), thickness*2, (255,255,255), -1)
            
            elif mode == "spray":
                # Create spray effect with random points
                for _ in range(20):
                    random_x = x + np.random.randint(-thickness*2, thickness*2)
                    random_y = y + np.random.randint(-thickness*2, thickness*2)
                    if 0 <= random_x < canvas_size[0] and 0 <= random_y < canvas_size[1]:
                        cv2.circle(current_img, (random_x, random_y), 1, color, -1)

    # Handle mouse button release
    elif event == cv2.EVENT_LBUTTONUP:
        if drawing:
            current_img = layers[current_layer]
            
            # Draw shapes on mouse release
            if mode == "circle":
                radius = int(((x - start_x)**2 + (y - start_y)**2)**0.5)
                cv2.circle(current_img, (start_x, start_y), radius, color, -1 if fill_mode else thickness)
            
            elif mode == "rectangle":
                cv2.rectangle(current_img, (start_x, start_y), (x, y), color, -1 if fill_mode else thickness)
            
            elif mode == "line":
                cv2.line(current_img, (start_x, start_y), (x, y), color, thickness)
            
            elif mode == "triangle":
                # Calculate triangle points
                mid_x = start_x + (x - start_x)//2
                top_y = start_y - abs(x - start_x)//2
                
                if fill_mode:
                    # Fill triangle
                    pts = np.array([[start_x, start_y], [mid_x, top_y], [x, y]], np.int32)
                    cv2.fillPoly(current_img, [pts], color)
                else:
                    # Draw triangle outline
                    cv2.line(current_img, (start_x, start_y), (x, y), color, thickness)
                    cv2.line(current_img, (start_x, start_y), (mid_x, top_y), color, thickness)
                    cv2.line(current_img, (x, y), (mid_x, top_y), color, thickness)
            
            save_state()
            drawing = False

# ============ MAIN APPLICATION LOOP ============
def main():
    """Main application function"""
    global color, thickness, mode, fill_mode, text, grid_on, current_layer, layers, history, redo_stack

    # Initialize window
    cv2.namedWindow("Advanced Drawing APP")
    cv2.setMouseCallback("Advanced Drawing APP", draw)

    # Print user manual
    print("\nAdvanced Drawing APP Manual:")
    print("1. Left click to start drawing")
    print("2. Press 'm' to cycle through modes:", ", ".join(modes))
    print("3. Press 'c' to cycle through colors: Blue, Green, Red")
    print("4. Press '+'/'-' to adjust thickness")
    print("5. Press 'f' to toggle fill mode")
    print("6. Press 'g' to toggle grid")
    print("7. Press 'n' to add new layer")
    print("8. Press 'l' to switch layers")
    print("9. Press 'Ctrl+Z' to undo")
    print("10. Press 'Ctrl+Y' to redo")
    print("11. Press 's' to save")
    print("12. Press 'o' to open")
    print("13. Press 'r' to reset canvas")
    print("14. Press 'q' to quit\n")

    # Main event loop
    while True:
        # Compose final image from all layers
        composite_image = layers[0].copy()
        for layer in layers[1:]:
            mask = layer != 255
            composite_image[mask] = layer[mask]

        # Add grid if enabled
        display_image = draw_grid(composite_image) if grid_on else composite_image
        
        # Display the image and handle keyboard input
        cv2.imshow("Advanced Drawing APP", display_image)
        key = cv2.waitKey(1) & 0xFF

        # Handle keyboard commands
        if key == ord('q'):
            break
        elif key == ord('m'):
            idx = modes.index(mode)
            mode = modes[(idx + 1) % len(modes)]
            print(f"Mode changed to: {mode}")
        elif key == ord('c'):
            colors = [(255,0,0), (0,255,0), (0,0,255)]  # Blue, Green, Red
            idx = colors.index(color)
            color = colors[(idx + 1) % len(colors)]
            print(f"Color changed to: {'Red' if color==(0,0,255) else 'Green' if color==(0,255,0) else 'Blue'}")
        elif key == ord('+'):
            thickness = min(thickness + 1, 10)
            print(f"Thickness increased to: {thickness}")
        elif key == ord('-'):
            thickness = max(thickness - 1, 1)
            print(f"Thickness decreased to: {thickness}")
        elif key == ord('f'):
            fill_mode = not fill_mode
            print(f"Fill mode: {'On' if fill_mode else 'Off'}")
        elif key == ord('g'):
            grid_on = not grid_on
            print(f"Grid: {'On' if grid_on else 'Off'}")
        elif key == ord('n'):
            layers.append(np.ones((canvas_size[1], canvas_size[0], 3)) * 255)
            current_layer = len(layers) - 1
            print(f"New layer added. Total layers: {len(layers)}")
        elif key == ord('l'):
            current_layer = (current_layer + 1) % len(layers)
            print(f"Switched to layer {current_layer + 1}/{len(layers)}")
        elif key == ord('z'):
            if history:
                redo_stack.append(layers[current_layer].copy())
                layers[current_layer] = history.pop()
                print("Undo")
        elif key == ord('y'):
            if redo_stack:
                history.append(layers[current_layer].copy())
                layers[current_layer] = redo_stack.pop()
                print("Redo")
        elif key == ord('s'):
            root = Tk()
            root.withdraw()
            file_path = filedialog.asksaveasfilename(defaultextension=".png")
            if file_path:
                cv2.imwrite(file_path, composite_image)
                print(f"Image saved as {file_path}")
            root.destroy()
        elif key == ord('o'):
            root = Tk()
            root.withdraw()
            file_path = filedialog.askopenfilename()
            if file_path:
                loaded_img = cv2.imread(file_path)
                if loaded_img is not None:
                    layers = [loaded_img]
                    current_layer = 0
                    history.clear()
                    redo_stack.clear()
                    print(f"Opened {file_path}")
            root.destroy()
        elif key == ord('r'):
            layers = [np.ones((canvas_size[1], canvas_size[0], 3)) * 255]
            current_layer = 0
            history.clear()
            redo_stack.clear()
            print("Canvas reset")

    cv2.destroyAllWindows()

# ============ PROGRAM ENTRY POINT ============
if __name__ == "__main__":
    main() 