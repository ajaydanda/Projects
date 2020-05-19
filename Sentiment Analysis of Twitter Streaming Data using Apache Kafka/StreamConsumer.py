import nltk
from kafka import KafkaConsumer
import json
from vaderSentiment.vaderSentiment import SentimentIntensityAnalyzer
from elasticsearch import Elasticsearch
from textblob import TextBlob


elastic_search_obj = Elasticsearch()
score_object = SentimentIntensityAnalyzer()


def main():
    # set-up a Kafka consumer
    consumer = KafkaConsumer("twitter_sentiment")
    for msg in consumer:
        dict_data = json.loads(msg.value)
        tweet = TextBlob(dict_data["text"])
        sentiment_score = score_object.polarity_scores(tweet)
        positive_score = sentiment_score['pos']
        negative_score = sentiment_score['neg']
        neutral_score = sentiment_score['neu']
        compound_score = sentiment_score['compound']
        if compound_score >= 0.05:
            sentiment_class = "Positive"
        elif (compound_score > -0.05) and (compound_score < 0.05):
            sentiment_class = "Neutral"
        else:
            sentiment_class = "Negative"
        print(tweet+"\t The sentiment of tweet is : " + sentiment_class)
        # add text and sentiment info to elasticsearch
        elastic_search_obj.index(index="tweet_sentiment_coronavirus",
                                 doc_type="test-type",
                                 body={"author": dict_data["user"]["screen_name"],
                                       "date": dict_data["created_at"],
                                       "message": dict_data["text"],
                                       "positive_score": positive_score,
                                       "negative_score": negative_score,
                                       "neutral_score": neutral_score,
                                       "compound_score": compound_score,
                                       "sentiment_class": sentiment_class})
        print('\n')


if __name__ == "__main__":
    main()
