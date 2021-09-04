option = {
    color: ["#333", "#e01f54", '#e87c25', '#fcce10', '#3fb1e3', '#6be6c1', '#c4ebad'],
    title: {
        text: 'mssql-1.5zipf'
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

            data: [30.62208,31.09414,31.8848,28.31144,26.8324]
        },
        {
            name: '读未提交隔离级别+Java锁',
            type: 'line',

            data: [55.57578,56.27744,55.92624,56.53711,50.73012]
        },
        {
            name: '读已提交隔离级别+Java锁',
            type: 'line',

            data: [55.056,55.09404,54.5325,56.36736,49.61034]
        },
        {
            name: '可重复读隔离级别+Java锁',
            type: 'line',

            data: [54.95126,54.23556,54.23556,55.8926,48.66633]
        },
        {
            name: '读未提交隔离级别+Redis锁',
            type: 'line',

            data: [43.20144, 51.70088, 44.67375, 39.62719, 34.35795]
        },

        {
            name: '读已提交隔离级别+Redis锁',
            type: 'line',

            data: [42.81674,51.21594,44.086,37.35882,32.68257]
        },
        {
            name: '可重复读隔离级别+Redis锁',
            type: 'line',

            data: [36.43269,44.04704,35.09024,30.65664,29.087]
        },
    ]
};