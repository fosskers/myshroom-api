MyShroom
========

MyShroom is a mushroom identification system that uses Neural Networks.
Our model was based off of AlexNet, and was trained with
[caffe](http://caffe.berkeleyvision.org/).

## API Description

While you can submit an image URL to [our website](http://myshroom.ca),
you can also make calls to the `/find` endpoint to yield JSON results
directly. Example:

```
$> curl "http://myshroom.ca/find?deadly=1&limit=1"
[{ "latin":"Gyromitra esculenta",
    "common": [
        "Beefsteak morel",
        "Lorchel"
    ],
    "confusedWith":[],
    "regions":[
        "Scandinavia",
        "Eastern Europe",
        "Upper Great Lakes of North America"
    ],
    "habitat":"Grows on sandy soil in temperate coniferous forest.",
    "fairyRings":false,
    "attributes":{
        "psychoactive":false,
        "poisonous":false,
        "deadly":true,
        "cap":["convex"],
        "hymenium":["irregular"],
        "sporePrint":[
            "yellow",
            "buff"
        ],
        "ecology":[
            "saprotrophic",
            "mycorrihizal"
        ]
    }
}]
```

The available parameters are:

Parameter | Argument | Find mushrooms that...
--------- | -------- | ----------------------
*none*    | *none*   | match any criteria
psycho    | `true` or `false` | ...have psychoactive effects when eaten
poison    | `true` or `false` | ...are poisonous, but not necessarily deadly
deadly    | `true` or `false` | ...are deadly when eaten
rings     | `true` or `false` | ...form fairy rings
cap       | *See below*       | ...have a certain cap type?
hymenium  | *See below*       | ...have a certain hymenium type?
spores    | *See below*       | ...have a certain spore print colour?
ecology   | *See below*       | ...grow in a certain way?
region    | Country / Continent name | ...grow in a certain area on Earth?

Notes:

1. `0` and `1` can be used in place of `false` and `true`.
2. Any or all of these parameters can be mixed for specific searches.

#### Cap Types
- conical
- convex
- depressed
- flat
- indistinct
- infundibuliform
- offset
- umbonate

#### Hymenium Types
- adnate
- adnexed
- decurrent
- free
- irregular
- subdecurrent

#### Spore Print Colours
- black
- black-brown
- brown
- buff
- cream
- pink
- reddish-brown
- salmon
- white
- yellow

#### Ecologies
- mycorrhizal
- parasitic
- saprotrophic

## TO-DO list

### Next Steps

- [ ] Website Extensions
  - [x] Extend `find` call to include all parameter types
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
- [ ] Database Improvements
  - [ ] For each `regions` listing that has a country, include a continent
- [ ] Misc.
  - [ ] Image rights?
  - [ ] Contact Mycological societies about what they would find useful

## Resources

### Papers

- [Mushroom Recognition - Damien Matti](http://mmspg.epfl.ch/files/content/sites/mmspl/files/shared/Semesterproject_mushroomrecognition.pdf)

### Mushroom Picture and Info Databases

- ImageNet
- [Mushroom Source](http://www.mushroomsource.com/mushrooms.html)
- [MycoKey](http://www.mycokey.com/newMycoKeySite/MycoKeyIdentQuick.html)
- [Roger Mushrooms](http://www.rogersmushrooms.com/)
- [UCI Mushroom Database](https://archive.ics.uci.edu/ml/datasets/Mushroom) - **Not actually useful**
- [Champis (French)](http://champis.net/wiki/index.php?title=Accueil)

### Mycological Societies

- [Mycological Society of Toronto](https://www.myctor.org/)
- [Vancouver Mycological Society](http://www.vanmyco.com/)
- [Alberta Mycologocial Society](http://www.wildmushrooms.ws/)
- [Mycological Society of America](http://msafungi.org/)

### Funding
http://www.canadabusiness.ca/eng/program/results/sgc-59/pof-193-194-195/

- (01) Canada Small Business Financing program
- (03) Futurpreur Canada startup program
- (04) Western Innovation initiative
- (16) Industrial Research Assistance Program
- (50) Scientific Research and Experimental Development Tax Incentive Program

http://www.nrcan.gc.ca/forests/federal-programs/13137
