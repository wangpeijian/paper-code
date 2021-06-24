option = {
    color: ["#333", "#e01f54", '#e87c25',  '#3fb1e3', '#6be6c1'],
    title: {
        text: 'pgsql-1.5zipf'
    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data: ['ssi', 'java_rc', 'java_rr', 'redis_rc', 'redis_rr']
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
        min: 0,
        max: 100,
        name: '吞吐量(qps)'
    },
    series: [
        {
            name: 'ssi',
            type: 'line',

            data: [55.26429,60.9402,69.5156,66.3942,62.17629]
        },

        {
            name: 'java_rc',
            type: 'line',

            data: [63.46548,64.6443,65.94198,62.64168,62.29224]
        },
        {
            name: 'java_rr',
            type: 'line',

            data: [59.26461,64.207,65.1816,62.8864,61.59105]
        },

        {
            name: 'redis_rc',
            type: 'line',

            data: [48.62986,56.5461,48.79872,47.99048,46.5296]
        },
        {
            name: 'redis_rr',
            type: 'line',

            data: [42.73598,45.81603,48.51656,46.508,45.84645]
        },
    ]
};