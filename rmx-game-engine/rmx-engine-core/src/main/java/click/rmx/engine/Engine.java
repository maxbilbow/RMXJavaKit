package click.rmx.engine;

/**
 * Created by Max on 26/09/2015.
 */

/**
 * Native bindings to the <a href="http://www.glfw.org/docs/latest/">GLFW</a> library.
 *
 * <p>GLFW is a free, Open Source, multi-platform library for opening a window, creating an OpenGL context and managing input. It is easy to integrate into
 * existing applications and does not lay claim to the main loop.</p>
 */
public class Engine {


        /** The major version number of the GLFW library. This is incremented when the API is changed in non-compatible ways. */
        public static final int GLFW_VERSION_MAJOR = 0x3;

        /** The minor version number of the GLFW library. This is incremented when features are added to the API but it remains backward-compatible. */
        public static final int GLFW_VERSION_MINOR = 0x1;

        /** The revision number of the GLFW library. This is incremented when a bug fix release is made that does not contain any API changes. */
        public static final int GLFW_VERSION_REVISION = 0x1;

        /** The key or button was released. */
        public static final int GLFW_RELEASE = 0x0;

        /** The key or button was pressed. */
        public static final int GLFW_PRESS = 0x1;

        /** The key was held down until it repeated. */
        public static final int GLFW_REPEAT = 0x2;

        /** The unknown key. */
        public static final int GLFW_KEY_UNKNOWN = 0xFFFFFFFF;

        /** Printable keys. */
        public static final int
                GLFW_KEY_SPACE         = 0x20,
                GLFW_KEY_APOSTROPHE    = 0x27,
                GLFW_KEY_COMMA         = 0x2C,
                GLFW_KEY_MINUS         = 0x2D,
                GLFW_KEY_PERIOD        = 0x2E,
                GLFW_KEY_SLASH         = 0x2F,
                GLFW_KEY_0             = 0x30,
                GLFW_KEY_1             = 0x31,
                GLFW_KEY_2             = 0x32,
                GLFW_KEY_3             = 0x33,
                GLFW_KEY_4             = 0x34,
                GLFW_KEY_5             = 0x35,
                GLFW_KEY_6             = 0x36,
                GLFW_KEY_7             = 0x37,
                GLFW_KEY_8             = 0x38,
                GLFW_KEY_9             = 0x39,
                GLFW_KEY_SEMICOLON     = 0x3B,
                GLFW_KEY_EQUAL         = 0x3D,
                GLFW_KEY_A             = 0x41,
                GLFW_KEY_B             = 0x42,
                GLFW_KEY_C             = 0x43,
                GLFW_KEY_D             = 0x44,
                GLFW_KEY_E             = 0x45,
                GLFW_KEY_F             = 0x46,
                GLFW_KEY_G             = 0x47,
                GLFW_KEY_H             = 0x48,
                GLFW_KEY_I             = 0x49,
                GLFW_KEY_J             = 0x4A,
                GLFW_KEY_K             = 0x4B,
                GLFW_KEY_L             = 0x4C,
                GLFW_KEY_M             = 0x4D,
                GLFW_KEY_N             = 0x4E,
                GLFW_KEY_O             = 0x4F,
                GLFW_KEY_P             = 0x50,
                GLFW_KEY_Q             = 0x51,
                GLFW_KEY_R             = 0x52,
                GLFW_KEY_S             = 0x53,
                GLFW_KEY_T             = 0x54,
                GLFW_KEY_U             = 0x55,
                GLFW_KEY_V             = 0x56,
                GLFW_KEY_W             = 0x57,
                GLFW_KEY_X             = 0x58,
                GLFW_KEY_Y             = 0x59,
                GLFW_KEY_Z             = 0x5A,
                GLFW_KEY_LEFT_BRACKET  = 0x5B,
                GLFW_KEY_BACKSLASH     = 0x5C,
                GLFW_KEY_RIGHT_BRACKET = 0x5D,
                GLFW_KEY_GRAVE_ACCENT  = 0x60,
                GLFW_KEY_WORLD_1       = 0xA1,
                GLFW_KEY_WORLD_2       = 0xA2;

        /** Function keys. */
        public static final int
                GLFW_KEY_ESCAPE        = 0x100,
                GLFW_KEY_ENTER         = 0x101,
                GLFW_KEY_TAB           = 0x102,
                GLFW_KEY_BACKSPACE     = 0x103,
                GLFW_KEY_INSERT        = 0x104,
                GLFW_KEY_DELETE        = 0x105,
                GLFW_KEY_RIGHT         = 0x106,
                GLFW_KEY_LEFT          = 0x107,
                GLFW_KEY_DOWN          = 0x108,
                GLFW_KEY_UP            = 0x109,
                GLFW_KEY_PAGE_UP       = 0x10A,
                GLFW_KEY_PAGE_DOWN     = 0x10B,
                GLFW_KEY_HOME          = 0x10C,
                GLFW_KEY_END           = 0x10D,
                GLFW_KEY_CAPS_LOCK     = 0x118,
                GLFW_KEY_SCROLL_LOCK   = 0x119,
                GLFW_KEY_NUM_LOCK      = 0x11A,
                GLFW_KEY_PRINT_SCREEN  = 0x11B,
                GLFW_KEY_PAUSE         = 0x11C,
                GLFW_KEY_F1            = 0x122,
                GLFW_KEY_F2            = 0x123,
                GLFW_KEY_F3            = 0x124,
                GLFW_KEY_F4            = 0x125,
                GLFW_KEY_F5            = 0x126,
                GLFW_KEY_F6            = 0x127,
                GLFW_KEY_F7            = 0x128,
                GLFW_KEY_F8            = 0x129,
                GLFW_KEY_F9            = 0x12A,
                GLFW_KEY_F10           = 0x12B,
                GLFW_KEY_F11           = 0x12C,
                GLFW_KEY_F12           = 0x12D,
                GLFW_KEY_F13           = 0x12E,
                GLFW_KEY_F14           = 0x12F,
                GLFW_KEY_F15           = 0x130,
                GLFW_KEY_F16           = 0x131,
                GLFW_KEY_F17           = 0x132,
                GLFW_KEY_F18           = 0x133,
                GLFW_KEY_F19           = 0x134,
                GLFW_KEY_F20           = 0x135,
                GLFW_KEY_F21           = 0x136,
                GLFW_KEY_F22           = 0x137,
                GLFW_KEY_F23           = 0x138,
                GLFW_KEY_F24           = 0x139,
                GLFW_KEY_F25           = 0x13A,
                GLFW_KEY_KP_0          = 0x140,
                GLFW_KEY_KP_1          = 0x141,
                GLFW_KEY_KP_2          = 0x142,
                GLFW_KEY_KP_3          = 0x143,
                GLFW_KEY_KP_4          = 0x144,
                GLFW_KEY_KP_5          = 0x145,
                GLFW_KEY_KP_6          = 0x146,
                GLFW_KEY_KP_7          = 0x147,
                GLFW_KEY_KP_8          = 0x148,
                GLFW_KEY_KP_9          = 0x149,
                GLFW_KEY_KP_DECIMAL    = 0x14A,
                GLFW_KEY_KP_DIVIDE     = 0x14B,
                GLFW_KEY_KP_MULTIPLY   = 0x14C,
                GLFW_KEY_KP_SUBTRACT   = 0x14D,
                GLFW_KEY_KP_ADD        = 0x14E,
                GLFW_KEY_KP_ENTER      = 0x14F,
                GLFW_KEY_KP_EQUAL      = 0x150,
                GLFW_KEY_LEFT_SHIFT    = 0x154,
                GLFW_KEY_LEFT_CONTROL  = 0x155,
                GLFW_KEY_LEFT_ALT      = 0x156,
                GLFW_KEY_LEFT_SUPER    = 0x157,
                GLFW_KEY_RIGHT_SHIFT   = 0x158,
                GLFW_KEY_RIGHT_CONTROL = 0x159,
                GLFW_KEY_RIGHT_ALT     = 0x15A,
                GLFW_KEY_RIGHT_SUPER   = 0x15B,
                GLFW_KEY_MENU          = 0x15C,
                GLFW_KEY_LAST          = GLFW_KEY_MENU;

        /** If this bit is set one or more Shift keys were held down. */
        public static final int GLFW_MOD_SHIFT = 0x1;

        /** If this bit is set one or more Control keys were held down. */
        public static final int GLFW_MOD_CONTROL = 0x2;

        /** If this bit is set one or more Alt keys were held down. */
        public static final int GLFW_MOD_ALT = 0x4;

        /** If this bit is set one or more Super keys were held down. */
        public static final int GLFW_MOD_SUPER = 0x8;

        /** Mouse buttons. See <a href="http://www.glfw.org/docs/latest/input.html#input_mouse_button">mouse button input</a> for how these are used. */
        public static final int
                GLFW_MOUSE_BUTTON_1      = 0x0,
                GLFW_MOUSE_BUTTON_2      = 0x1,
                GLFW_MOUSE_BUTTON_3      = 0x2,
                GLFW_MOUSE_BUTTON_4      = 0x3,
                GLFW_MOUSE_BUTTON_5      = 0x4,
                GLFW_MOUSE_BUTTON_6      = 0x5,
                GLFW_MOUSE_BUTTON_7      = 0x6,
                GLFW_MOUSE_BUTTON_8      = 0x7,
                GLFW_MOUSE_BUTTON_LAST   = GLFW_MOUSE_BUTTON_8,
                GLFW_MOUSE_BUTTON_LEFT   = GLFW_MOUSE_BUTTON_1,
                GLFW_MOUSE_BUTTON_RIGHT  = GLFW_MOUSE_BUTTON_2,
                GLFW_MOUSE_BUTTON_MIDDLE = GLFW_MOUSE_BUTTON_3;

        /** Joysticks. See <a href="http://www.glfw.org/docs/latest/input.html#joystick">joystick input</a> for how these are used. */
        public static final int
                GLFW_JOYSTICK_1    = 0x0,
                GLFW_JOYSTICK_2    = 0x1,
                GLFW_JOYSTICK_3    = 0x2,
                GLFW_JOYSTICK_4    = 0x3,
                GLFW_JOYSTICK_5    = 0x4,
                GLFW_JOYSTICK_6    = 0x5,
                GLFW_JOYSTICK_7    = 0x6,
                GLFW_JOYSTICK_8    = 0x7,
                GLFW_JOYSTICK_9    = 0x8,
                GLFW_JOYSTICK_10   = 0x9,
                GLFW_JOYSTICK_11   = 0xA,
                GLFW_JOYSTICK_12   = 0xB,
                GLFW_JOYSTICK_13   = 0xC,
                GLFW_JOYSTICK_14   = 0xD,
                GLFW_JOYSTICK_15   = 0xE,
                GLFW_JOYSTICK_16   = 0xF,
                GLFW_JOYSTICK_LAST = GLFW_JOYSTICK_16;



        /** Window attributes. */
        public static final int
                GLFW_FOCUSED      = 0x20001,
                GLFW_ICONIFIED    = 0x20002,
                GLFW_RESIZABLE    = 0x20003,
                GLFW_VISIBLE      = 0x20004,
                GLFW_DECORATED    = 0x20005,
                GLFW_AUTO_ICONIFY = 0x20006,
                GLFW_FLOATING     = 0x20007;

        /** Input options. */
        public static final int
                GLFW_CURSOR               = 0x33001,
                GLFW_STICKY_KEYS          = 0x33002,
                GLFW_STICKY_MOUSE_BUTTONS = 0x33003;

        /** Cursor state. */
        public static final int
                GLFW_CURSOR_NORMAL   = 0x34001,
                GLFW_CURSOR_HIDDEN   = 0x34002,
                GLFW_CURSOR_DISABLED = 0x34003;

        /** Standard cursor shapes. See <a href="http://www.glfw.org/docs/latest/input.html#cursor_standard">standard cursor creation</a> for how these are used. */
        public static final int
                GLFW_ARROW_CURSOR     = 0x36001,
                GLFW_IBEAM_CURSOR     = 0x36002,
                GLFW_CROSSHAIR_CURSOR = 0x36003,
                GLFW_HAND_CURSOR      = 0x36004,
                GLFW_HRESIZE_CURSOR   = 0x36005,
                GLFW_VRESIZE_CURSOR   = 0x36006;

        /** Monitor events. */
        public static final int
                GLFW_CONNECTED    = 0x40001,
                GLFW_DISCONNECTED = 0x40002;

        /** Don't care value. */
        public static final int GLFW_DONT_CARE = 0xFFFFFFFF;

        /** PixelFormat hints. */
        public static final int
                GLFW_RED_BITS         = 0x21001,
                GLFW_GREEN_BITS       = 0x21002,
                GLFW_BLUE_BITS        = 0x21003,
                GLFW_ALPHA_BITS       = 0x21004,
                GLFW_DEPTH_BITS       = 0x21005,
                GLFW_STENCIL_BITS     = 0x21006,
                GLFW_ACCUM_RED_BITS   = 0x21007,
                GLFW_ACCUM_GREEN_BITS = 0x21008,
                GLFW_ACCUM_BLUE_BITS  = 0x21009,
                GLFW_ACCUM_ALPHA_BITS = 0x2100A,
                GLFW_AUX_BUFFERS      = 0x2100B,
                GLFW_STEREO           = 0x2100C,
                GLFW_SAMPLES          = 0x2100D,
                GLFW_SRGB_CAPABLE     = 0x2100E,
                GLFW_REFRESH_RATE     = 0x2100F,
                GLFW_DOUBLE_BUFFER    = 0x21010;

        /** Client API hints. */
        public static final int
                GLFW_CLIENT_API               = 0x22001,
                GLFW_CONTEXT_VERSION_MAJOR    = 0x22002,
                GLFW_CONTEXT_VERSION_MINOR    = 0x22003,
                GLFW_CONTEXT_REVISION         = 0x22004,
                GLFW_CONTEXT_ROBUSTNESS       = 0x22005,
                GLFW_OPENGL_FORWARD_COMPAT    = 0x22006,
                GLFW_OPENGL_DEBUG_CONTEXT     = 0x22007,
                GLFW_OPENGL_PROFILE           = 0x22008,
                GLFW_CONTEXT_RELEASE_BEHAVIOR = 0x22009;

        /** Values for the {@link #GLFW_CLIENT_API CLIENT_API} hint. */
        public static final int
                GLFW_OPENGL_API    = 0x30001,
                GLFW_OPENGL_ES_API = 0x30002;

        /** Values for the {@link #GLFW_CONTEXT_ROBUSTNESS CONTEXT_ROBUSTNESS} hint. */
        public static final int
                GLFW_NO_ROBUSTNESS         = 0x0,
                GLFW_NO_RESET_NOTIFICATION = 0x31001,
                GLFW_LOSE_CONTEXT_ON_RESET = 0x31002;

        /** Values for the {@link #GLFW_OPENGL_PROFILE OPENGL_PROFILE} hint. */
        public static final int
                GLFW_OPENGL_ANY_PROFILE    = 0x0,
                GLFW_OPENGL_CORE_PROFILE   = 0x32001,
                GLFW_OPENGL_COMPAT_PROFILE = 0x32002;

        /** Values for the {@link #GLFW_CONTEXT_RELEASE_BEHAVIOR CONTEXT_RELEASE_BEHAVIOR} hint. */
        public static final int
                GLFW_ANY_RELEASE_BEHAVIOR   = 0x0,
                GLFW_RELEASE_BEHAVIOR_FLUSH = 0x35001,
                GLFW_RELEASE_BEHAVIOR_NONE  = 0x35002;
}
