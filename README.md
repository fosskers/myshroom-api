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
*none*    | *none*   | ...match any criteria
psycho    | `true` or `false` | ...have psychoactive effects when eaten
poison    | `true` or `false` | ...are poisonous, but not necessarily deadly
deadly    | `true` or `false` | ...are deadly when eaten
rings     | `true` or `false` | ...form fairy rings
cap       | *See below*       | ...have a certain cap type
hymenium  | *See below*       | ...have a certain hymenium type
spores    | *See below*       | ...have a certain spore print colour
ecology   | *See below*       | ...grow in a certain way
region    | Country / Continent name | ...grow in a certain area on Earth

Notes:

1. `0` and `1` can be used in place of `false` and `true`.
2. Any or all of these parameters can be mixed for specific searches.
3. Use `limit=n`, where `n` is a number, to limit the quantity of results.

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
