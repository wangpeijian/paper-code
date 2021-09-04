option = {
    color: ["#333", "#e01f54", '#e87c25', '#fcce10', '#3fb1e3', '#6be6c1', '#c4ebad'],
    // title: {
    //      text: 'SQL Server事务吞吐量趋势',
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
        right: '15%',
        bottom: '3%',
        containLabel: true
    },

    xAxis: {
        type: 'category',
        boundaryGap: false,
        data: ['1', '10', '20'],
        name: '事务中锁定资源（个）'
    },
    yAxis: {
        type: 'value',
        min: 40,
        max: 270,
        name: '吞吐量(qps)'
    },
    series: [
        {
            name: '可串行化隔离级别',
            type: 'line',
            data: [238.4613,73.38786, 50.10096]
        },
        {
            name: '可重复读隔离级别+Redis锁',
            type: 'line',
            data: [216.06164,99.0851,62.28765]
        },
        {
            name: '读已提交隔离级别+Redis锁',
            type: 'line',
            data: [223.77976,100.33925, 65.49525]
        },
        {
            name: '读未提交隔离级别+Redis锁',
            type: 'line',
            data: [228.53816, 107.10315,68.07411 ]
        },
        {
            name: '可重复读隔离级别+Java锁',
            type: 'line',
            data: [250.68615 ,117.32014,80.40869 ]
        },
        {
            name: '读已提交隔离级别+Java锁',
            type: 'line',
            data: [258.50409, 121.62024, 82.71384]
        },
        {
            name: '读未提交隔离级别+Java锁',
            type: 'line',
            data: [261.60414,124.25424, 83.2524]
        },
    ]
};