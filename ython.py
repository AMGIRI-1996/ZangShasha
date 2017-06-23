import sys
from nltk.corpus import wordnet
import numpy as np
import re

y = []
#print(sys.argv[1])
#print(sys.argv[2])
post = sys.argv[3];
#and pos_1.group(2)== post
for w1 in wordnet.synsets(sys.argv[1]):
	for w2 in wordnet.synsets(sys.argv[2]):
		try:
			pos_1 = re.match(r'(.*)\.(.*)\.(.*)\.(.*)',str(w1.lemmas()[0]))
			pos_2 = re.match(r'(.*)\.(.*)\.(.*)\.(.*)',str(w2.lemmas()[0]))
			if post == "a":
				if (pos_2.group(2)=="a" or pos_2.group(2)=="s") and (pos_1.group(2)=="a" or pos_1.group(2)=="s"):
					x = w1.wup_similarity(w2);
					if(type(x) == float):
						#print("Hello")
						y.append(x)
			else:
				if pos_1.group(2)==pos_2.group(2) and pos_1.group(2)==post:
					x = w1.wup_similarity(w2);
					if(type(x) == float):
						#print("Hello")
						y.append(x)

		except:
			print("0%")
#print(y)
if len(y)>0:
	if sys.argv[1]==sys.argv[2]:
		print(10)
	else:
		print(np.mean(y)*10)
else:
	if sys.argv[1]==sys.argv[2]:
		print(10)
	else:
		print(0)