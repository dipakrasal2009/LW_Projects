import numpy as np
import cv2

drawing = False
mode = "circle"
start_x, start_y = -1, -1
color = (255, 0, 0)
thickness = 2

def draw(event, x, y, flags, param):
    global drawing, start_x, start_y, img, mode, color, thickness
    if event == cv2.EVENT_LBUTTONDOWN:
        drawing = True
        start_x, start_y = x, y
    elif event == cv2.EVENT_MOUSEMOVE:
        if drawing:
            if mode.lower() == "freehand":
                cv2.line(img, (start_x, start_y), (x, y), color, thickness)
                start_x, start_y = x, y
    elif event == cv2.EVENT_LBUTTONUP:
        drawing = False
        if mode == "circle":
            radius = int(((x - start_x)**2 + (y - start_y)**2)**0.5)
            cv2.circle(img, (start_x, start_y), radius, color, thickness)
        elif mode == "rectangle":
            cv2.rectangle(img, (start_x, start_y), (x, y), color, thickness)
        elif mode == "line":
            cv2.line(img, (start_x, start_y), (x, y), color, thickness)

print("Manual of the app:")
print("1. Left click to start drawing")
print("2. Press 'm' to change the mode: circle, rectangle, line, freehand")
print("3. Press 'c' to change the color: red, green, blue")
print("4. Press '+' or '-' to increase or decrease the thickness")
print("5. Press 's' to save")
print("Press 'q' to quit")

img = np.ones((600, 800, 3)) * 255
cv2.namedWindow("Drawing APP")
cv2.setMouseCallback("Drawing APP", draw)

while True:
    cv2.imshow("Drawing APP", img)
    key = cv2.waitKey(1) & 0xFF
    
    if key == ord('q'):
        break
    elif key == ord('m'):
        modes = ["circle", "rectangle", "line", "freehand"]
        current_index = modes.index(mode.lower())
        mode = modes[(current_index + 1) % len(modes)]
        print(f"Mode changed to: {mode}")
    elif key == ord('c'):
        colors = [(255, 0, 0), (0, 255, 0), (0, 0, 255)]  # Blue, Green, Red
        current_index = colors.index(color)
        color = colors[(current_index + 1) % len(colors)]
        print(f"Color changed to: {'Red' if color==(0,0,255) else 'Green' if color==(0,255,0) else 'Blue'}")
    elif key == ord('+'):
        thickness = min(thickness + 1, 10)
        print(f"Thickness increased to: {thickness}")
    elif key == ord('-'):
        thickness = max(thickness - 1, 1)
        print(f"Thickness decreased to: {thickness}")
    elif key == ord('s'):
        cv2.imwrite('drawing.png', img)
        print("Image saved as drawing.png")

cv2.destroyAllWindows()