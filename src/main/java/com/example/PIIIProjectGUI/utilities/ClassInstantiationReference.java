    package com.example.PIIIProjectGUI.utilities;

    public class ClassInstantiationReference {

        // Method to dynamically load and instantiate a class based on package and class name
        @SuppressWarnings("unchecked") // Reflection returns Object; callers know the concrete item type
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

        @SuppressWarnings("unchecked") // Reflection returns Object; callers know the concrete item type
        public static <T> T createWeaponInstance(String className) {
            try {
                // Load the class dynamically
                String classPath = "com.example.PIIIProjectGUI.implementation.classes.items.weapons" + "." + className;
                Class<?> loadedClass = Class.forName(classPath);

                // Create and return an instance of the class
                // Cast the instance to the desired type (T)
                return (T) loadedClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                // Handle any errors during reflection
                throw new IllegalArgumentException("Unable to instantiate class: " + className, e);
            }
        }

        @SuppressWarnings("unchecked") // Reflection returns Object; callers know the concrete item type
        public static <T> T createPotionInstance(String className) {
            try {
                // Load the class dynamically
                String classPath = "com.example.PIIIProjectGUI.implementation.classes.items.potions" + "." + className;
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
