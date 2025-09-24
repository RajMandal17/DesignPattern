# Decorator Pattern Implementation

## Overview
The **Decorator Pattern** is a structural design pattern that allows behavior to be added to an individual object, either statically or dynamically, without affecting the behavior of other objects from the same class. It is often used to extend the functionality of classes in a flexible and reusable way.

## Intent
- Attach additional responsibilities to an object dynamically.
- Provide a flexible alternative to subclassing for extending functionality.
- Allow responsibilities to be added and removed at runtime.

## Motivation
Imagine you have a pizza ordering system where customers can customize their pizzas with various toppings. Instead of creating subclasses for every possible combination (e.g., MargheritaWithExtraCheese, MargheritaWithExtraCheeseAndOlives), the Decorator Pattern allows you to dynamically add toppings to a base pizza.

## Structure
The Decorator Pattern consists of the following components:

1. **Component**: An interface or abstract class defining the operations that can be altered by decorators.
2. **Concrete Component**: A class that implements the Component interface.
3. **Decorator**: An abstract class that implements the Component interface and contains a reference to a Component object.
4. **Concrete Decorator**: Classes that extend the Decorator and add specific behavior.

## Implementation in Pizza Example

### Component Interface
```java
interface Pizza {
    String getDescription();
    double getCost();
}
```

### Concrete Components
```java
class PlainPizza implements Pizza {
    // Implementation for basic pizza
}

class MargheritaPizza implements Pizza {
    // Implementation for Margherita pizza
}
```

### Abstract Decorator
```java
abstract class PizzaDecorator implements Pizza {
    protected Pizza pizza;

    public PizzaDecorator(Pizza pizza) {
        this.pizza = pizza;
    }
}
```

### Concrete Decorators
```java
class ExtraCheese extends PizzaDecorator {
    // Adds extra cheese functionality
}

class Olives extends PizzaDecorator {
    // Adds olives functionality
}

class StuffedCrust extends PizzaDecorator {
    // Adds stuffed crust functionality
}
```

## How It Works
1. Start with a concrete component (e.g., `MargheritaPizza`).
2. Wrap it with decorators to add functionality (e.g., `new ExtraCheese(new Olives(new StuffedCrust(margheritaPizza)))`).
3. Each decorator adds its own behavior while delegating to the wrapped component.

## Benefits
- **Flexibility**: Add or remove responsibilities dynamically.
- **Open/Closed Principle**: Classes are open for extension but closed for modification.
- **Composition over Inheritance**: Avoids class explosion from multiple inheritance.
- **Single Responsibility**: Each decorator has a single responsibility.

## When to Use
- When you need to add responsibilities to objects dynamically and transparently.
- When extension by subclassing is impractical or impossible.
- When you want to avoid a large number of subclasses for every combination of features.

## Running the Demo
To run the demonstration:
```bash
./run_demo.sh
```

This will compile and execute the code, showing how decorators can be stacked to customize a pizza.

## Output Example
```
Pizza Description: Margherita Pizza, Extra Cheese, Olives, Stuffed Crust
Total Cost: ₹320.0
```

## Related Patterns
- **Adapter Pattern**: Changes an object's interface.
- **Composite Pattern**: Treats individual objects and compositions uniformly.
- **Strategy Pattern**: Changes object's behavior by changing its strategy.