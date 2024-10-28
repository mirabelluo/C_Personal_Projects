//4AD13F

#include <stdio.h>
#include <string.h>

int main(void) {

    int coin_denominations = 0;
    char add_type = 'a';
    int add_num = 0;
    char subtract_type = 'a';
    int subtract_num = 0;
    char type = 'a';
    int value = 0;
    int index = 0;
    char command = 'q';
    int coin_found = 0;

    const int COIN_DENOM = 20; //max coin types

    char coin_identifiers[COIN_DENOM];
    int coin_values[COIN_DENOM];
    int counter[COIN_DENOM]; 
    
    for (int j = 0; j < COIN_DENOM; j++) {  //initializes counter sets all to 0
        counter[j] = 0;
    }

    //prompts user to enter number of different coin denominations
    printf("How many denominations? ");
    int denom_scanner = scanf("%d", &coin_denominations);

    //error handling: invalid coin denominations
    if ((coin_denominations > 20) || (denom_scanner != 1)) {
        fprintf(stderr, "Invalid input\n");
        return 1;
    }

    //prompts user for one-letter identifier and value of coin for each denomination
    for (int i = 0; i < coin_denominations; i++) {
        printf("Enter coin identifier and value in cents: ");
        int value_scanner = scanf(" %c %d", &type, &value); //scans coin type and value into respective arrays

        //error handling: invalid coin identifier/value
        if (value_scanner != 2) {
            fprintf(stderr,"Invalid input\n");
            return 1;
        }
        coin_identifiers[i] = type;
        coin_values[i] = value;
    }

    printf("Enter a command: ");

    while (scanf(" %c", &command) != EOF) { //checks for EOF allows user input ctl-D to terminate
        
        if (command == 'q') { // exits loop and ends program
            break;
        }

        index = 0;
        coin_found = 0;

        switch (command) {
            case 'a': //adds to the count of how many coins of a specified type are in the user’s collection

                //error handling: checks if valid identifier and count
                //scans in valid identifier and counts
                if (scanf(" %c%d", &add_type, &add_num) != 2) {
                    fprintf(stderr,"Invalid input\n");
                    return 1;
                }

                //checks for coin type
                while ((index < coin_denominations) && (coin_identifiers[index] != add_type)) {
                    index++;
                }
                
                //increments counter by specified value and checks if value is in bounds
                if (index < coin_denominations) {
                    counter[index] += add_num;
                    coin_found = 1; //verifies that coin was found
                }

                //error handling: if coin not found
                if (!coin_found) {
                    fprintf(stderr, "Unknown coin identifier\n");
                    return 2;
                }

                break;

            case 'r': //subtracts from the count of how many coins of a specified type are in the user’s collection
                
                //error handling: checks if valid identifier and count
                //scans in valid identifier and counts
                if (scanf(" %c%d", &subtract_type, &subtract_num) != 2) {
                    fprintf(stderr,"Invalid input\n");
                    return 1;
                }

                //checks for coin type
                while ((index < coin_denominations) && (coin_identifiers[index] != subtract_type)) {
                    index++;
                }
                
                //increments counter by specified value and checks if value is in bounds
                if (index < coin_denominations) {
                    counter[index] -= subtract_num;
                    coin_found = 1; //verifies that coin was found
                }

                //error handling: if coin not found
                if (!coin_found) {
                    fprintf(stderr, "Unknown coin identifier\n");
                    return 2;
                }
                break;

            case 's':
                //summarizes the user’s coin collection
                printf("Identifier,Face Value,Count,Total Value\n");

                int all_total_cents = 0;

                for (int a = 0; a < coin_denominations; a++) { //prints each coin's summary

                    int total_val = coin_values[a] * counter[a];
                    char coin_type = coin_identifiers[a];
                    int indv_value = coin_values[a];
                    int num_coins = counter[a];

                    printf("%c,%d,%d,%d\n",coin_type,indv_value,num_coins,total_val);

                    all_total_cents += total_val;
                }

                //converts total coin value into $X.YY form
                int dollar_total = all_total_cents / 100;
                int remaining_cents = all_total_cents % 100;

                printf("Overall value of collection: $%d.%02d\n",dollar_total,remaining_cents);  
                break;
                
            default:
                fprintf(stderr, "Invalid command\n");
                return 3;
                break;
            }
        printf("Enter a command: ");
        }
    printf("Bye!\n");
    return 0;
}
