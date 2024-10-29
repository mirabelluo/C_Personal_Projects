#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Mon Sep 11 11:24:11 2023

@author: Paola Garcia (lgarci27@jhu.edu)

Gateway Python Project - Sentiment Analysis

"""

import extras

def main():
    """
    sentiment_ analysis main
    """
   
    # Define a list of positive keywords
    positive_keywords = ["good", "excellent", "fantastic", "wonderful", "amazing", "great", "love", "comfortable", "friendly", "excellent", "nice", "clean", "helpful", "pleasant", "thanks", "amazing", "upgrade"]
   
    # Define a list of negative keywords
    negative_keywords = ["bad", "terrible", "horrible", "awful", "sad", "late", "poor", "return", "warning", "hard,", "cramped", "difficult", "problem", "sin", "never", "regret", "worst"]
   
    # Define unwanted characters
    unwanted_characters = '!"#$%&\'()*+,-./:;<=>?@[\\]^_`{|}~'
   
    # Define a list of stop words (customized for airlines)
    stop_words = ["fly", "united","ua","i", "im", "me", "my", "myself", "we", "our", "ours", "why", "ourselves", "you", "your", "yours", "yourself", "yourselves", "he", "him", "his", "himself", "she", "her", "hers", "herself", "it", "its", "itself", "they", "them", "their", "theirs", "themselves", "what", "which", "who", "whom", "this", "that", "these", "those", "am", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had", "having", "do", "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or", "because", "as", "until", "while", "of", "at", "by", "for", "with", "about", "against", "between", "into", "through", "during", "before", "after", "above", "below", "to", "from", "up", "down", "in", "out", "on", "off", "over", "under", "again", "further", "then", "once", "so", "just", "no", "not", "would", "will", "get", "ever", "now", "can", "how"]

    reviews = extras.reading_csv_tweet('final_United_Tweets.csv')
   
    # Analyze sentiments
    sentiment_scores = []
    #iterates over every element in list
    for review in reviews:
        sentiment_scores.append(analyze_sentiment(review, positive_keywords, negative_keywords, unwanted_characters, stop_words))

    # Extract aspects
    positive_aspects, negative_aspects, neutral_aspects = extract_aspects(reviews, positive_keywords, negative_keywords, unwanted_characters, stop_words)
   
    # Visualize the sentiment distribution (text-based output)
    sentiment_distribution(sentiment_scores)
   
    # Display the top positive and negative aspects
    extract_rank_aspect("positive", positive_aspects, 5)
    extract_rank_aspect("negative", negative_aspects, 5)
    extract_rank_aspect("neutral", neutral_aspects, 5)
             


####################
## Preprocess
####################

def lower_split(text):
    '''
    This function converts a string to lowercase

    Parameters
    ----------
    text : str
        A phrase to be analyzed

    Returns
    -------
    words : list
        All of the words in (str) in lowercase

    '''
   
    lower_word = text.lower()
    words = lower_word.split()
    return words

def unwanted_characters_filter(text, unwanted_characters):
    '''
    This function returns a string without any unwanted characters

    Parameters
    ----------
    text : str
        A string of the words to clean
    unwanted_characters : Tstr
        A string with all unwanted characters

    Returns
    -------
    output : TYPE
        DESCRIPTION.

    '''
    output = ''
   
    #creates a list of text that includes correct spacing
    spaced1 = text.replace("", '$')
 
    list_txt = spaced1.split('$')
   
    #creates a list of all unwanted characters
    spaced2 = unwanted_characters.replace("",' ')
    list_unwanted = spaced2.split()

    for char in list_txt:
        if char not in list_unwanted:
            output += char
   
    return output

def unwanted_numbers(text):
    '''
    This function returns a string that constains phfrases with no numbers

    Parameters
    ----------
    text : str
        A phrase with or without numbers

    Returns
    -------
    output : str
        A clean version of the input without numbers
    '''
    return unwanted_characters_filter(text, '0123456789')
               
def unwanted_words(text, stop_words):
    '''
    This function removes stop words from a string

    Parameters
    ----------
    text : str
        A str containing some phrase
    stop_words : list
        Stop words, or words commonly used in any language, tailored for this task

    Returns
    -------
    output : str
        Clean version of the phrase without stop words

    '''
    list_txt = lower_split(text)
    templist = []
    
    for word in list_txt:
        if word not in stop_words:
            templist.append(word)
    
    return ' '.join(templist)
   
def concatenating_clean_words(tokens, unwanted_characters):
    '''
    This function concatenates the clean words from a list

    Parameters
    ----------
    tokens : list
        A list of words
    unwanted_characters : str
        A string that contains all unwanted characters

    Returns
    -------
    unwanted_characters_filter(tokens_str, unwanted_characters) : str
        A string without all the unwanted characters
        DESCRIPTION.

    '''
   
    #translates tokens into a string
    tokens_str = ' '.join(tokens)
   
    return unwanted_characters_filter(tokens_str, unwanted_characters)

def preprocess_text(text, unwanted_characters, stop_words):  
    '''
    This function fully cleans the phrase, removing all unnecessary characters/words and returns a filtered text

    Parameters
    ----------
    text : str
        Unfiltered text to be cleaned
    unwanted_characters : str
        Unwanted characters
    stop_words : list
        Words not uuseful in analysis

    Returns
    -------
    num_removed : str
        Fully cleaned phrase

    '''
   
    #lowered = lower_split(text)
    lowered = text.lower()
    #lowered = [x.lower() for x in text]
   
    char_filtered = unwanted_characters_filter(lowered, unwanted_characters)
   
    stop_removed = unwanted_words(char_filtered, stop_words)
   
    num_removed = unwanted_numbers(stop_removed)
   
    return num_removed

####################
##  Analyze sentiment
####################

def word_search(keywords, words):
    '''
    This function counts the number of keywords in a list words from the text

    Parameters
    ----------
    keywords : list
        Words to search
    words : list
        Words in the text

    Returns
    -------
    counter : int
        Number of keywords in the text

    '''
    counter = 0
   
    for w in words:
        if w in keywords:
            counter += 1
    return counter

def analyze_sentiment(text, positive_keywords, negative_keywords, unwanted_characters, stop_words):
    '''
    This function analyzes the sentiment of a text and gives a numerical score measuring satisfaction

    Parameters
    ----------
    text : str
        Words to be analyzed
    positive_keywords : list
        Given positive keywords to analyze the problem
    negative_keywords : list
        Given negative keywords to analyze the problem
    unwanted_characters : str
        Unwanted characters
    stop_words : list
        Words not useful for analysis

    Returns
    -------
    sentiment_score : int
        A numerical score for satisfaction

    '''
   
    preprocessed = preprocess_text(text, unwanted_characters, stop_words)
    words = preprocessed.split()
   
    num_pos = word_search(positive_keywords, words)
    num_neg = word_search(negative_keywords, words)
   
    satisfaction = num_pos * 2
    dissatisfaction = num_neg * 2
   
    sentiment_score = satisfaction - dissatisfaction
   
    return sentiment_score
 
####################
##  Extract aspects
####################

def extract_aspects(text_list, positive_keywords,negative_keywords, unwanted_characters,stop_words):
    '''
    This function computes the word frequency in positive and negative contexts while subtracting the counts of positive and negative keywords

    Parameters
    ----------
    texts : list
        List of texts to be analyzed
    positive_keywords : list
        List of given positive keywords
    negative_keywords : list
        List of given negative keywords
    unwanted_characters : str
        Unwanted characters
    stop_words : list
        List of words that are not useful for the analysis

    Returns
    -------
    sorted_positive_aspects : list
        Number of word occurences - positive
    sorted_negative_aspects : list
        Number of word occurences - negative
    sorted_neutral_aspects : list
        Number of word occurences - neutral
    '''
    positive_aspects = {}
    negative_aspects = {}
    neutral_aspects = {}
   
   
    for t in text_list:
     
     sentiment_score = analyze_sentiment(t, positive_keywords, negative_keywords, unwanted_characters, stop_words)
     words = preprocess_text(t, unwanted_characters, stop_words)
     
     for word in words.split():
         if sentiment_score > 0:
             if word not in positive_aspects and word not in positive_keywords:
                 positive_aspects[word] = 1
             elif word in positive_aspects and word not in positive_keywords:
                 positive_aspects[word] += 1
         elif sentiment_score < 0:
             if word not in negative_aspects and word not in negative_keywords:
                 negative_aspects[word] = 1
             elif word in negative_aspects and word not in negative_keywords:
                 negative_aspects[word] += 1
         elif sentiment_score == 0:
             if word not in neutral_aspects and word not in negative_keywords and word not in positive_keywords:
                 neutral_aspects[word] = 1
             elif word in neutral_aspects and word not in negative_keywords and word not in positive_keywords:
                 neutral_aspects[word] += 1
   
   
    #orders aspects by frequency
    sorted_positive_aspects = sorted(positive_aspects.items(), key = lambda x: x[1], reverse = True)
    sorted_negative_aspects = sorted(negative_aspects.items(), key = lambda x: x[1], reverse = True)
    sorted_neutral_aspects = sorted(neutral_aspects.items(), key = lambda x: x[1], reverse = True)
   
    #turns dictionary into list with sublists
    list_positive_aspects = []
    list_negative_aspects = []
    list_neutral_aspects = []
   
    for item in sorted_positive_aspects:
        list_positive_aspects.append(list(item))
   
    for item in sorted_negative_aspects:
        list_negative_aspects.append(list(item))
   
    for item in sorted_neutral_aspects:
        list_neutral_aspects.append(list(item))

   
    return list_positive_aspects, list_negative_aspects, list_neutral_aspects
   
####################
##  Visualization
####################

def sentiment_distribution (sentiment_scores):
    '''
    This function computes and visualizes the sentiment distribution of the sentiment scores

    Parameters
    ----------
    sentiment_scores : list
        Scores given by analyze_sentiment

    Returns
    -------
    distribution_list : list
        Distribution of the positive, negative, and neutral scores
    '''
   
    distribution_list = [0, 0, 0]
   
    for score in sentiment_scores:
        if score > 0:
            distribution_list[0] += 1
        elif score < 0:
            distribution_list[1] += 1
        elif score == 0:
            distribution_list[2] += 1
   
    extras.pie_plot(distribution_list)
   
    return distribution_list

def extract_rank_aspect(aspect_category, aspects, num):
    '''
    Extracts and ranks the n most positive, negative, or neutral aspects

    Parameters
    ----------
    aspect_category : str
        Denotes the type of aspect
    aspects : list
        DESCRIPTION.
    num : TYPE
        DESCRIPTION.

    Returns
    -------
    None.

    '''
    print(f'Top {aspect_category} aspects:')
    if num > len(aspects):
        for n in range(0, len(aspects)):
            print(f'{aspects[n][0]}: {aspects[n][1]}')
    else:
        for n in range(0, num):
            print(f'{aspects[n][0]}: {aspects[n][1]}')

if __name__ == "__main__":
    main()