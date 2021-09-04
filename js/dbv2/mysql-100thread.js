option = {
    color: ["#333", "#e01f54", '#e87c25', '#fcce10', '#3fb1e3', '#6be6c1', '#c4ebad'],

    // title: {
    //     text: 'MySQL事务不同冲突系数的吞吐量趋势',
    //     left: 'center',
    // },

    tooltip: {
        trigger: 'axis'
    },

    legend: {
        top: ' 0%',
        data: ['可串行化隔离级别', '可重复读隔离级别+Redis锁', '读已提交隔离级别+Redis锁', '读未提交隔离级别+Redis锁', '可重复读隔离级别+Java锁', '读已提交隔离级别+Java锁', '读未提交隔离级别+Java锁']
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
        data: ['0.6', '0.8', '1.0', '1.2', '1.4'],
        name: 'zipf系数'
    },
    yAxis: {
        type: 'value',
        min: 50,
        max: 170,
        name: '吞吐量(qps)'
    },

    series: [
        {
            name: '可串行化隔离级别',
            type: 'line',
            data: [133.947, 120.74034, 95.43096, 63.09925, 51.62833]
        },
        {
            name: '可重复读隔离级别+Redis锁',
            type: 'line',
            data: [142.88535, 134.6, 100.79736, 64.117, 53.53612]
        },
        {
            name: '读已提交隔离级别+Redis锁',
            type: 'line',
            data: [143.7, 137.102, 104.3058, 65.7968, 56.79828]
        },
        {
            name: '读未提交隔离级别+Redis锁',
            type: 'line',
            data: [143.1, 138.8, 104.84383, 66.77728, 55.93185]
        },
        {
            name: '可重复读隔离级别+Java锁',
            type: 'line',
            data: [155.8, 150.42874, 121.2, 75.9, 62]
        },
        {
            name: '读已提交隔离级别+Java锁',
            type: 'line',
            data: [157.8, 155.6, 124.4, 78.1, 64.9]
        },
        {
            name: '读未提交隔离级别+Java锁',
            type: 'line',
            data: [157.75266, 157.28427, 126.3, 78.8, 66.2]
        },
    ]
};