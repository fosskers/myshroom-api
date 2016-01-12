MyShroom
========

MyShroom is a mushroom identification system that uses Neural Networks.
Our model was based off of AlexNet, and was trained with
[caffe](http://caffe.berkeleyvision.org/).

# TO-DO list

## Next Steps

- [ ] Website Extensions
  - [ ] Extend `find` call to include all parameter types
  - [x] On success, redirect to nice page
  - [ ] Include reprentative images of all shrooms in `/public/images`
  - [ ] Sexy deployment (`supervisor`? `lemons`?)
  - [ ] Create a topbar
  - [ ] Include a *Waiver* page for legal things
  - [ ] Incldue a *Data* page for listing the sizes of our image sets
  - [ ] Migration to Haskell
    - [ ] Go through Parallel/Concurrency book
    - [ ] Look into `servant` library
    - [ ] Write new server to mimick the Scala
- [ ] NeuralNet Revamp
  - [ ] Research and choose new ML stack (TensorFlow?)
  - [ ] Train model with new stack
  - [ ] Create server in ML lib's host language to serve JSON
  - [ ] Tweak API server to access this
- [ ] Misc.
  - [ ] Image rights?
  - [ ] Contact Mycological societies about what they would find useful

# Resources

## Papers

- [Mushroom Recognition - Damien Matti](http://mmspg.epfl.ch/files/content/sites/mmspl/files/shared/Semesterproject_mushroomrecognition.pdf)

## Mushroom Picture and Info Databases

- ImageNet
- [Mushroom Source](http://www.mushroomsource.com/mushrooms.html)
- [MycoKey](http://www.mycokey.com/newMycoKeySite/MycoKeyIdentQuick.html)
- [Roger Mushrooms](http://www.rogersmushrooms.com/)
- [UCI Mushroom Database](https://archive.ics.uci.edu/ml/datasets/Mushroom) - **Not actually useful**
- [Champis (French)](http://champis.net/wiki/index.php?title=Accueil)

## Mycological Societies

- [Mycological Society of Toronto](https://www.myctor.org/)
- [Vancouver Mycological Society](http://www.vanmyco.com/)
- [Alberta Mycologocial Society](http://www.wildmushrooms.ws/)
- [Mycological Society of America](http://msafungi.org/)

## Funding
http://www.canadabusiness.ca/eng/program/results/sgc-59/pof-193-194-195/

- (01) Canada Small Business Financing program
- (03) Futurpreur Canada startup program
- (04) Western Innovation initiative
- (16) Industrial Research Assistance Program
- (50) Scientific Research and Experimental Development Tax Incentive Program

http://www.nrcan.gc.ca/forests/federal-programs/13137
