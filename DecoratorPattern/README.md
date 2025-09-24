# Decorator Pattern

## Introduction
Structural design patterns focus on composing classes and objects into flexible structures. The Decorator Pattern allows adding behavior to objects dynamically without affecting others.

## What is Decorator Pattern?
A structural pattern that wraps objects to add responsibilities at runtime, keeping the original interface intact.

## Real-Life Analogy
Coffee shop: Start with plain coffee, add milk, sugar, whipped cream dynamically without new classes for each combination.

## Problem Solved
Avoids "class explosion" from inheritance when combining behaviors (e.g., Pizza with multiple toppings).

## Solution
Use composition: Wrap base objects with decorators that add features.

## Key Components
- **Component Interface**: Defines operations (e.g., `Pizza` with `getDescription()`, `getCost()`)
- **Concrete Components**: Base implementations (e.g., `PlainPizza`, `MargheritaPizza`)
- **Abstract Decorator**: Wraps component, forwards calls
- **Concrete Decorators**: Add specific behavior (e.g., `ExtraCheese`, `Olives`)

## How It Works
1. Create base pizza
2. Wrap with decorators: `new ExtraCheese(new Olives(pizza))`
3. Each decorator adds to description/cost

## Benefits
- Dynamic composition
- Follows Open/Closed Principle
- Avoids subclass explosion
- Single responsibility per decorator

## When to Use
- Add responsibilities dynamically
- Avoid many subclasses
- Need flexible, composable behaviors

## Disadvantages
- Many small classes
- Complex debugging
- Runtime overhead

## Real-World Examples
- Food delivery apps (custom toppings)
- Text editors (formatting: bold, italic)

## Running Demo
```bash
./run_demo.sh
```
Output: `Pizza Description: Margherita Pizza, Extra Cheese, Olives, Stuffed Crust | Total Cost: ₹320.0`