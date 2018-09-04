% tabella di comodo per mostrare le stime dell'errore
Table = cell2table(cell(0,2));
Table.Properties.VariableNames = {'n' 'errore'};


f = @(x) 1./(1 + x.^2); % Funzione di Runge
a = -6;
b = 6;

n = 2;
while n <= 40
    
    xi = ceby(n, a, b); % ascisse di chebyshev
    fi = f(xi); % valutazioni fun di runge
    p = @(x) lagrange(xi, fi, x); % lagrange
    e = @(x) abs(f(x) - p(x)); % errore
    
    % grafici
    figure(1)
    fplot(f, [a, b], 'g')
    hold on
    fplot(p, [a, b], 'r--')
    % punti di interpolazione
    plot(xi, fi, 'ro')
    legend('Funzione di Runge','Approssimazione della funzione', 'Ascisse di Chebyshev')
    hold off
    print('-dpng', strcat('graficiEs7/es4_7_img', num2str(n), '.png'));
    
    % grafico errore
    figure(2)
    x = linspace(0, b, 100001);
    plot(x, e(x), 'DisplayName', strcat('n = ', num2str(n)))
    hold on
    
    % errore
    x = linspace(a, b, 100001);
    e = norm(f(x) - p(x), inf);
    record = {n, e};
    Table = [Table; record];

    n = n + 2;

end



% mostra la tabella
figure(3)
uitable('Data',Table{:,:},'ColumnName',Table.Properties.VariableNames,...
    'RowName',Table.Properties.RowNames,'Units', 'Normalized', 'Position',[0, 0, 1, 1]);


function xi = ceby(n, a, b)
% function xi = ceby(n, a, b)
% n numbero di ascisse da cercare
% intervallo da a a b
% xi vettore con le ascisse cercate di Chebyshev
xi = zeros(n+1, 1);
for i = 0:n
    xi(n+1-i) = (a+b)/2 + cos(pi*(2*i+1)/(2*(n+1)))*(b-a)/2;
end
end

function y = lagrange(xi, fi, x)
% function y = lagrange( xi, fi, x )
% xi vettore dei punti di ascissa
% fi vettore dei valori di f(x)
% x vettore di punti in cui calcolare f(x)
% y valore di f(x)
if length(xi) ~= length(fi)
    error('xi e fi hanno lunghezza diversa, deve essere uguale')
end
n = length(xi)-1;
m = length(x);
y = zeros(size(x));
for i=1:m
    for j=1:n+1
        p = 1;
        for k=1:n+1
            if j~=k
                p = p*(x(i)-xi(k))/(xi(j)-xi(k));
            end
        end
        y(i) = y(i)+fi(j)*p;
    end
end
return
end