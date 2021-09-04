option = {
    textStyle: {
        fontSize: 16,
    },
    color: ["#333", "#e01f54", '#fcce10', '#3fb1e3', '#c4ebad'],
    title: {
        text: 'pgsql-1.5zipf'
    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data: ['ssi', '读已提交隔离级别+Java锁', '可重复读隔离级别+Java锁', '读已提交隔离级别+Redis锁', '可重复读隔离级别+Redis锁']
    },
    grid: {
        top: '10%',
        left: '3%',
        right: '10%',
        bottom: '3%',
        containLabel: true
    },

    xAxis: {
        axisLabel: {
            fontSize: 16,
        },
        type: 'category',
        boundaryGap: false,
        data: ['50', '100', '150', '200', '250'],
        name: '线程数'
    },
    yAxis: {
        axisLabel: {
            fontSize: 16,
        },
        type: 'value',
        min: 20,
        max: 100,
        name: '吞吐量(qps)'
    },
    series: [
        {
            name: 'ssi',
            type: 'line',

            data: [55.26429, 60.9402, 63.5152, 61.2157, 60.63191]
        },

        {
            name: '读已提交隔离级别+Java锁',
            type: 'line',

            data: [63.46548, 64.6443, 65.94198, 62.64168, 62.29224]
        },
        {
            name: '可重复读隔离级别+Java锁',
            type: 'line',

            data: [59.26461, 64.207, 65.1816, 62.8864, 61.59105]
        },

        {
            name: '读已提交隔离级别+Redis锁',
            type: 'line',

            data: [48.62986, 56.5461, 54.52479, 51.43005, 50.2626]
        },
        {
            name: '可重复读隔离级别+Redis锁',
            type: 'line',

            data: [42.73598, 45.81603, 43.6425, 42.4268, 41.31617]
        },
    ]
};