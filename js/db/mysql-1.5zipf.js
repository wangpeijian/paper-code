option = {
    color: ["#333", "#e01f54", '#e87c25', '#fcce10', '#3fb1e3', '#6be6c1', '#c4ebad'],
    title: {
        text: 'mysql-1.5zipf'
    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data: ['ssi', 'java_ru', 'java_rc', 'java_rr','redis_ru', 'redis_rc', 'redis_rr']
    },
    grid: {
        top: '10%',
        left: '3%',
        right: '10%',
        bottom: '3%',
        containLabel: true
    },

    xAxis: {
        type: 'category',
        boundaryGap: false,
        data: ['50', '100', '150', '200', '250'],
        name: '线程数'
    },
    yAxis: {
        type: 'value',
        min: 20,
        max: 100,
        name: '吞吐量(qps)'
    },
    series: [
        {
            name: 'ssi',
            type: 'line',

            data: [49.83808,52.15872,51.82947,50.2447, 45.52141]
        },
        {
            name: 'java_ru',
            type: 'line',

            data: [56.9487,59.84157,59.84609,57.72486,55.6661]
        },
        {
            name: 'java_rc',
            type: 'line',

            data: [56.03268,59.49268,59.7402,56.12694,54.33453]
        },
        {
            name: 'java_rr',
            type: 'line',

            data: [54.33472,56.28042,58.4298,56.09884,51.42222]
        },
        {
            name: 'redis_ru',
            type: 'line',

            data: [54.51685, 53.9504, 55.97193, 53.93871, 49.74912]
        },

        {
            name: 'redis_rc',
            type: 'line',

            data: [53.24313,54.18,55.29342,53.83226,47.09613]
        },
        {
            name: 'redis_rr',
            type: 'line',

            data: [52.8546,53.9504,53.60706,52.55852,43.19008]
        },
    ]
};