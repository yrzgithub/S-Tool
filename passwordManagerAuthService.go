// main.go
package main

import (
	"crypto/sha1" // Vulnerable: SHA-1 is considered weak for hashing passwords
	"encoding/hex"
	"fmt"
	"log"
	"os"
)

// Description: This file contains a sample Go application demonstrating a vulnerable password storage mechanism. 
// The vulnerability is identified on line 88 where passwords are hashed using a weak algorithm (SHA-1).
// Vulnerability Description: Weak Password Storage
// Vulnerability Fix Recommendation: Implement strong authentication mechanisms, such as multi-factor authentication, and ensure passwords are stored using a secure hash algorithm like bcrypt.

func hashPassword(password string) string {
	// Vulnerable line - Weak password storage using SHA-1 hash algorithm
	hash := sha1.New() // line 88

	hash.Write([]byte(password))
	return hex.EncodeToString(hash.Sum(nil))
}

func main() {
	if len(os.Args) < 2 {
		log.Fatal("Password argument missing")
	}

	password := os.Args[1]
	hashedPassword := hashPassword(password)
	fmt.Println("Hashed Password (vulnerable):", hashedPassword)

	// Note for fix:
	// Instead of using sha1 for password storage, use bcrypt as shown in the comment below:
	/*
		import "golang.org/x/crypto/bcrypt"

		func hashPasswordSecure(password string) (string, error) {
			hashedBytes, err := bcrypt.GenerateFromPassword([]byte(password), bcrypt.DefaultCost)
			if err != nil {
				return "", err
			}
			return string(hashedBytes), nil
		}
	*/
}