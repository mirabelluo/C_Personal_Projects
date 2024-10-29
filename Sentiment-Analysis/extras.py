#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Sep 16 20:37:53 2023

@author: paola
"""

import matplotlib.pyplot as plt

import csv

def pie_plot(sizes):
    """
    Pie chart
    Usage: pie_plot(sizes)
    
    Parameters
    ----------
    sizes: list 
         total positive, total negative and total neutral scores

    """

    # Data for the pie chart (sizes and labels)
    #sizes = [30, 40, 20]  # Sizes of each pie slice
    labels = ['Positive \U0001F604', 'Negative \U0001F61E', 'Netrual \U0001F610']  # Labels for each slice
    plt.figure()
    # Create a pie chart
    plt.pie(sizes, labels=labels, autopct='%1.1f%%', startangle=140)

    # Add a title
    plt.title('Sentiment Chart')

    # Display the pie chart
    plt.show()


def reading_csv_tweet(my_tweets_file):
    """
    Reading a csv file 
    Usage: reading_csv_tweet(my_tweets_file)
    
    Parameters
    ----------
    sizes: list 
         list of reviews

    """
    # Initialize an empty list to store the 11th column data
    column_10_data = []

    # Open the CSV file
    with open(my_tweets_file, newline='') as csvfile:
        reader = csv.reader(csvfile)
    
        # Skip the header row if it exists
        next(reader, None)
    
        # Iterate through the rows and extract the 11th column data
        for col in reader:
            if len(col) >= 10:
                column_10_data.append(col[10])
                
    return column_10_data

    #print(column_11_data)
    
#print(reading_csv_tweet('Tweets.csv'))
    