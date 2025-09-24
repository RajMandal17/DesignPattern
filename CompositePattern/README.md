# Composite Pattern

## Introduction
Structural pattern for composing objects into tree structures representing part-whole hierarchies. Allows uniform treatment of individual and composite objects.

## What is Composite Pattern?
Lets you compose objects into tree structures and work with them as if they were individual objects.

## Analogy
File system: Files (leaves) and directories (composites) treated uniformly for operations like size calculation.

## Problem Solved
Need to treat individual objects and groups uniformly without type-checking. Avoids code duplication and enables recursive structures.

## Solution
Define component interface, implement leaf (individual) and composite (container) classes that share the interface.
 
 
## Key Components
- **Component Interface**: Common operations (e.g., `getSize()`, `getName()`)
- **Leaf**: Individual objects (e.g., `File`)
- **Composite**: Containers holding children (e.g., `Directory`)

## How It Works
1. Define interface for all components
2. Leaves implement directly
3. Composites implement by delegating to children
4. Client treats all uniformly via interface

## Benefits
- Uniform treatment of objects
- Easy extension and recursion
- Cleaner client code
- Follows Open/Closed Principle

## When to Use
- Hierarchical structures (trees)
- Uniform operations on individuals and groups
- Avoid client-side type differentiation

## Disadvantages
- Can violate Single Responsibility
- Overkill for simple structures
- May hide important distinctions

## Real-World Examples
- File systems (files/directories)
- GUI components (buttons/containers)
- E-commerce product bundles

## Running Demo
```bash
./run_demo.sh
```
Output: File system hierarchy with size calculations.