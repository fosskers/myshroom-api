MyShroom
========

MyShroom is a mushroom identification system that uses Neural Networks.
Our model was based off of AlexNet, and was trained with
[caffe](http://caffe.berkeleyvision.org/).

# TO-DO list

## Initial Tasks

- [x] Write Scala API
- [x] Find mushroom pictures + name + info **NIMA**
  - [x] Create database from specific collections on ImageNet
  - [ ] Contact mycological societies for access to their info **NIMA**
- [x] Learn **Caffe** and its python interface **OMAR + JESSE**
- [x] Produce the official model once images and tags are collected
- ~~Write Django server to serve JSON on top of X labels and confidence values based given image~~
- [x] Report + Poster (in LaTeX) **OMAR**

## Next Steps

- [ ] Website Extensions
  - [ ] Extend `find` call to include all parameter types
  - [ ] On success, redirect to nice page
  - [ ] Include reprentative images of all shrooms in `/public/images`
  - [ ] Sexy deployment (`supervisor`? `lemons`?)
  - [ ] Migration to Haskell
    - [ ] Go through Parallel/Concurrency book
    - [ ] Look into `servant` library
    - [ ] Write new server to mimick the Scala
- [ ] NeuralNet Revamp
  - [ ] Research and choose new ML stack (TensorFlow?)
  - [ ] Train model with new stack
  - [ ] Create server in ML lib's host language to serve JSON
  - [ ] Tweak API server to access this

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
