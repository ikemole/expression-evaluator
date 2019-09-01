# Expression Evaluator
This is a Java library that can be used to solve a Math expression. 

The main API is the `ExpressionEvaluator.evaluate` method which accepts a String that represents a valid math expression, solves it 
and returns the result. 

## Usage
```
ExpressionEvaluator evaluator = new ExpressionEvaluator();
String expression = "5 + 4 ^ (1/2)";
double answer = evaluator.evaluate(expression); // 7.0
```

## Features
- Accepts any valid expression with the following operators: 
  - Multiplication: `*`
  - Division: `/`
  - Addition: `+`
  - Subtraction: `-`
  - Exponent: `^`
  - Brackets: `()`
- Supports deeply nested brackets

Examples of valid expressions: `"2 * 3"`, `"5 + 65 / 13 ^ 2"`, `"1 + (2 ^ (9 / 3)) * 8"`

An invalid expression will throw an exception.

### Constraints
- Each number in the expression must be an integer.
