    package com.example.PIIIProjectGUI.utilities;

    public class ClassInstantiationReference {

        // This method will return a reference to the desired class.
        // You have to provide the String relative path to the package containing the class
        // and the (String) name of the class itself.
        private static Class<?> convertToClassReference(String packageRelativePath, String className) {
            try {
                // Combine package and class name to create the full class path
                String classPath = packageRelativePath + "." + className;

                // Dynamically load the class
                return Class.forName(classPath);
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException("Class not found: " + packageRelativePath + "." + className, e);
            }
        }

        // This method will use the reference to the class to instantiate the object with.
        private static <T> T createInstanceFromClassReference(Class<T> type) {
            try {
                // Create a new instance of the class using reflection
                return type.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                // Handle any potential reflection issues (e.g., no default constructor)
                throw new IllegalArgumentException("Unable to instantiate class: " + type.getName(), e);
            }
        }

        // Method to dynamically load and instantiate a class based on package and class name
        public static <T> T createInstance(String packageRelativePath, String className) {
            try {
                // Load the class dynamically
                String classPath = packageRelativePath + "." + className;
                Class<?> loadedClass = Class.forName(classPath);

                // Create and return an instance of the class
                // Cast the instance to the desired type (T)
                return (T) loadedClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                // Handle any errors during reflection
                throw new IllegalArgumentException("Unable to instantiate class: " + className, e);
            }
        }

        public static <T> T createWeaponInstance(String className) {
            try {
                // Load the class dynamically
                String classPath = "src/main/java/com/example/PIIIProjectGUI/dataFiles/gameStateFiles/savedInventoryWeapons.csv" + "." + className;
                Class<?> loadedClass = Class.forName(classPath);

                // Create and return an instance of the class
                // Cast the instance to the desired type (T)
                return (T) loadedClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                // Handle any errors during reflection
                throw new IllegalArgumentException("Unable to instantiate class: " + className, e);
            }
        }

        public static <T> T createPotionInstance(String className) {
            try {
                // Load the class dynamically
                String classPath = "src/main/java/com/example/PIIIProjectGUI/dataFiles/gameStateFiles/savedInventoryPotions.csv" + "." + className;
                Class<?> loadedClass = Class.forName(classPath);

                // Create and return an instance of the class
                // Cast the instance to the desired type (T)
                return (T) loadedClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                // Handle any errors during reflection
                throw new IllegalArgumentException("Unable to instantiate class: " + className, e);
            }
        }

    }
