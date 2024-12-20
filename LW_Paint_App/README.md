# Advanced Paint Application

A feature-rich drawing application built with Python and OpenCV that provides multiple drawing tools, layers support, and various customization options.

## Features

- Multiple drawing tools:
  - Freehand drawing
  - Shapes (Circle, Rectangle, Triangle, Line)
  - Text input
  - Eraser
  - Spray paint effect

- Layer system for complex compositions
- Undo/Redo functionality
- Grid overlay option
- Adjustable brush thickness
- Multiple colors
- Fill/outline modes for shapes
- Save/Load functionality

## Requirements
python
pip install numpy opencv-python

## Usage

Run the application:

python
python advanced_paint.py

### Controls

- **Drawing Tools**
  - `m` - Cycle through drawing modes
  - `c` - Cycle through colors (Blue, Green, Red)
  - `+/-` - Adjust brush thickness
  - `f` - Toggle fill mode for shapes

- **Canvas Options**
  - `g` - Toggle grid overlay
  - `n` - Add new layer
  - `l` - Switch between layers

- **File Operations**
  - `s` - Save drawing
  - `o` - Open image
  - `r` - Reset canvas

- **Edit Operations**
  - `Ctrl+Z` - Undo
  - `Ctrl+Y` - Redo
  - `q` - Quit application

### Drawing Modes

1. **Freehand**: Free drawing with current brush settings
2. **Circle**: Click and drag to define radius
3. **Rectangle**: Click and drag to define corners
4. **Line**: Click and drag to create straight lines
5. **Triangle**: Click and drag to create triangles
6. **Text**: Click to place text
7. **Eraser**: Remove content
8. **Spray**: Create spray paint effect

## Technical Details

- Built with Python 3.x
- Uses OpenCV for image processing
- Numpy for array operations
- Tkinter for file dialogs
- Canvas size: 800x600 pixels
- Supports up to 20 undo/redo operations

## Contributing

Feel free to fork this project and submit pull requests with improvements. Some areas for potential enhancement:
- Additional drawing tools
- More color options
- Custom canvas sizes
- Brush styles
- Layer opacity
- Export to different file formats

## License

This project is open source and available under the LW License.
