// sample.js

// Description: A simple web application script with potential XSS vulnerability

// Import necessary modules
const express = require('express');
const app = express();
const bodyParser = require('body-parser');

// Configure middlewares
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

// Homepage route
app.get('/', (req, res) => {
  res.send(`
    <form method="POST" action="/submit">
      <input type="text" name="username" placeholder="Enter your username" />
      <button type="submit">Submit</button>
    </form>
  `);
});

// Vulnerable route
app.post('/submit', (req, res) => {
  const username = req.body.username;

  // Vulnerable line: Displaying user input directly without sanitization
  // This is the line number 42, which is vulnerable to XSS attack
  res.send(`<h1>Welcome, ${username}!</h1>`);

  // Note: If an attacker submits a "<script>" tag as the username, it will execute.
});

// Configure server
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}`);
});