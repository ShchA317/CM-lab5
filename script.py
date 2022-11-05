from prettytable import PrettyTable

def gauss_polynomial(dots, x):
    n = len(dots)
    y = [[0 for _ in range(n)] for __ in range(n)]
    h = dots[1][0] - dots[0][0]
    table = PrettyTable(['yi', 'dyi', 'd2yi', 'd3yi', 'd4yi', 'd5yi', 'd6yi'])
    row = []
    chosen = []
    for k in range(n):
        y[k][0] = dots[k][1]

    for i in range(1, n):
        for j in range(n - i):
            y[j][i] = y[j + 1][i - 1] - y[j][i - 1]
    for i in range(n):
        for j in range(n):
            row.append(round(y[i][j],6))
        table.add_row(row)
        row = []

    result = y[n // 2][0]
    chosen.append(result)
    t = (x - dots[n // 2][0]) / h
    for i in range(1, n):
        chosen.append(round(y[(n - i) // 2][i]))
    table.add_row(chosen)
    print(table)
    return result

dots = [[1.10,0.2234],[1.25,1.2438],[1.40,2.2644],[1.55,3.2984],[1.70,2.3222],[1.85,5.3516],[2.00,6.3867]]
gauss_polynomial(dots, 1.891)
