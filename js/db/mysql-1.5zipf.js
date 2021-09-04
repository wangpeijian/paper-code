option = {
    color: ["#333", "#e01f54", '#e87c25', '#fcce10', '#3fb1e3', '#6be6c1', '#c4ebad'],
    title: {
        text: 'mysql-1.5zipf'
    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data: ['ssi', '读未提交隔离级别+Java锁', '读已提交隔离级别+Java锁', '可重复读隔离级别+Java锁','读未提交隔离级别+Redis锁', '读已提交隔离级别+Redis锁', '可重复读隔离级别+Redis锁']
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
            name: '读未提交隔离级别+Java锁',
            type: 'line',

            data: [56.9487,59.84157,59.84609,57.72486,55.6661]
        },
        {
            name: '读已提交隔离级别+Java锁',
            type: 'line',

            data: [56.03268,59.49268,59.7402,56.12694,54.33453]
        },
        {
            name: '可重复读隔离级别+Java锁',
            type: 'line',

            data: [54.33472,56.28042,58.4298,56.09884,51.42222]
        },
        {
            name: '读未提交隔离级别+Redis锁',
            type: 'line',

            data: [54.51685, 53.9504, 55.97193, 53.93871, 49.74912]
        },

        {
            name: '读已提交隔离级别+Redis锁',
            type: 'line',

            data: [53.24313,54.18,55.29342,53.83226,47.09613]
        },
        {
            name: '可重复读隔离级别+Redis锁',
            type: 'line',

            data: [52.8546,53.9504,53.60706,52.55852,43.19008]
        },
    ]
};