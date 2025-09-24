# Facade Pattern

## Introduction
Structural pattern that provides a simplified interface to a complex subsystem. Like an automatic car hiding engine complexities from the driver.

## What is Facade Pattern?
Provides a unified interface to a set of interfaces in a subsystem, making it easier to use.

## Analogy
Manual car: Driver manages gears, clutch, etc. (complex). Automatic car: Driver just drives (simplified facade).

## Problem Solved
Hides complexity of interacting with multiple subsystems (e.g., payment, reservation, notifications) behind a single interface.

## Solution
Create a facade class that coordinates subsystem calls, exposing only high-level methods.

## Key Components
- **Subsystem Classes**: Individual services (e.g., `PaymentService`, `SeatReservationService`)
- **Facade Class**: Unified interface (e.g., `MovieBookingFacade`) that manages interactions

## How It Works
1. Client calls facade method (e.g., `bookMovieTicket()`)
2. Facade internally calls subsystem methods in order
3. Client gets simplified result

## Benefits
- Reduces coupling
- Simplifies client code
- Promotes layered architecture
- Improves testability

## When to Use
- Complex subsystems with many classes
- Need simpler API for clients
- Want to reduce client-subsystem coupling
- Layered architecture desired

## Disadvantages
- Can hide too much complexity
- Facade changes may affect clients
- Potential for "god object" if too many responsibilities

## Real-World Examples
- Movie booking systems (BookMyShow)
- Home theater systems (single remote controls multiple devices)

## Running Demo
```bash
./run_demo.sh
```
Output: Step-by-step booking process with confirmation.